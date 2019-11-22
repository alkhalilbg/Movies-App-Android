package com.alkhalilbg.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkhalilbg.moviesapp.database.AppDatabase;
import com.alkhalilbg.moviesapp.model.Moviee;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class FavoriteMovieDetail extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_id";
    public static final int DEFAULT_ID = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_movie_details);


        final TextView originalTitleTextView = findViewById(R.id.title_tv);
        final TextView overViewTextView = findViewById(R.id.overview_tv);
        final TextView releaseDateTextView = findViewById(R.id.release_date_tv);
        final TextView voteAverageTextView = findViewById(R.id.vote_average_tv);
        final ImageView posterImage = findViewById(R.id.poster_iv);

        final Intent intent = getIntent();
        final int id = intent.getIntExtra(EXTRA_ID, DEFAULT_ID);

        final LiveData<Moviee> movie = AppDatabase.getInstance(getApplicationContext()).movieDao().loadMovieById(id);
        movie.observe(this, new Observer<Moviee>() {
            @Override
            public void onChanged(@Nullable Moviee movie) {

                originalTitleTextView.setText(movie.getOriginalTitle());
                overViewTextView.setText(movie.getOverview());
                releaseDateTextView.setText(movie.getReleaseDate());
                voteAverageTextView.setText(String.valueOf(movie.getVoteAverage()));
                Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w185"
                        + movie.getMoviePoster()).into(posterImage);
            }
        });

    }
}

