package com.alkhalilbg.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alkhalilbg.moviesapp.database.AppDatabase;
import com.alkhalilbg.moviesapp.model.Moviee;
import com.alkhalilbg.moviesapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_FLAG = "extra_flag";
    public static final String EXTRA_ID = "extra_id";
    public static final int DEFAULT_POSITION = -1;
    public static final int DEFAULT_ID = -1;
    String mTrailerUrl;
    Moviee mMovie;
    String trailerData;
    JSONArray resultsArray2;
    String trailerKey;
    AppDatabase mDb;
    int mMovieId;
    int mPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        mDb = AppDatabase.getInstance(getApplicationContext());

        TextView originalTitleTextView = findViewById(R.id.title_tv);
        TextView overViewTextView = findViewById(R.id.overview_tv);
        TextView releaseDateTextView = findViewById(R.id.release_date_tv);
        TextView voteAverageTextView = findViewById(R.id.vote_average_tv);
        ImageView posterImage = findViewById(R.id.poster_iv);
        Button trailerButton = findViewById(R.id.trailer_button);
        Button deleteFromFavorite = findViewById(R.id.favorite_button_delete);
        Button reviewsButton = findViewById(R.id.reviews_button);


        final NetworkUtils networkUtils = new NetworkUtils();


        final Intent intent = getIntent();
        int position1 = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        String flag = intent.getStringExtra(EXTRA_FLAG);
        final int id = intent.getIntExtra(EXTRA_ID, DEFAULT_ID);
        if (flag.equals("A")) {
            mPosition = position1;
            JSONObject movieInfo = null;
            try {
                movieInfo = NetworkUtils.resultsArray.getJSONObject(mPosition);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            mMovie = networkUtils.paresJson(movieInfo);
        } else {
            mPosition = position1;
            final LiveData<Moviee> databaseMovie = AppDatabase.getInstance(getApplicationContext()).movieDao().loadMovieById(id);
            databaseMovie.observe(this, new Observer<Moviee>() {
                @Override
                public void onChanged(@Nullable Moviee movie) {
                    mMovie = movie;
                    Log.d("k", "mMovie: " + mMovie);
                }
            });
        }

        Log.d("k", "mMovie: " + mMovie);


        originalTitleTextView.setText(mMovie.getOriginalTitle());
        overViewTextView.setText(mMovie.getOverview());
        releaseDateTextView.setText(mMovie.getReleaseDate());
        voteAverageTextView.setText("" + mMovie.getVoteAverage());
        Picasso.with(this).load("https://image.tmdb.org/t/p/w185" + mMovie.getMoviePoster()).into(posterImage);

        final Moviee finalMovie = mMovie;
        mTrailerUrl = "https://api.themoviedb.org/3/movie/" + finalMovie.getMovieId()
                + "/videos?api_key=310e2ef8d44e911293fa5437f251d6c3&language";

        mMovieId = mMovie.getMovieId();


        new fetchTheMovieTrailerKey().execute();


        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + trailerKey);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(Intent.createChooser(intent1, "Open With"));
            }
        });


        Button favoriteButton = findViewById(R.id.favorite_button);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Moviee> moviesList = AppDatabase.getInstance(getApplicationContext()).movieDao().loadAllMovies2();
                        for (int i = 0; i < moviesList.size(); i++) {
                            if (mMovie.getMovieId() == moviesList.get(i).getMovieId()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "This movie already added to favorites", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }

                        }


                        mDb.movieDao().insertMovie(mMovie);


                    }
                });

            }
        });

        deleteFromFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppExecutors.getInstance().diskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        final List<Moviee> databaseMovies = AppDatabase.getInstance(getApplicationContext()).movieDao().loadAllMovies2();
                        for (int i = 0; i < databaseMovies.size(); i++) {
                            if (mMovie.getMovieId() == databaseMovies.get(i).getMovieId()) {
                                AppDatabase.getInstance(getApplicationContext()).movieDao()
                                        .deleteById(databaseMovies.get(i).getDatabaseItemId());
                            }
                        }
                    }
                });
            }
        });


        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Reviews.class);
                intent1.putExtra(Reviews.EXTRA_ID, mMovieId);
                startActivity(intent1);
            }
        });


    }


    public class fetchTheMovieTrailerKey extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                URL requestedUrl = new URL(mTrailerUrl);
                HttpURLConnection connection = (HttpURLConnection) requestedUrl.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                trailerData = IOUtils.toString(inputStream, (Charset) null);
                JSONObject jsonObjectT = new JSONObject(trailerData);
                resultsArray2 = jsonObjectT.getJSONArray("results");
                JSONObject o = resultsArray2.getJSONObject(0);
                trailerKey = o.getString("key");


            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(String v) {
            super.onPostExecute(v);

        }


    }
}
