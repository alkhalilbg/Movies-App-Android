package com.alkhalilbg.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.alkhalilbg.moviesapp.utils.NetworkUtils;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {


    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter = new MovieAdapter(this);
    NetworkUtils mNetworkUtils = new NetworkUtils();
    String mPopularMoviesUrl = "https://api.themoviedb.org/3/discover/movie?api_key=310e2ef8d44e911293fa5437f251d6c3&language=en-US&" +
            "sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    String mTopRatedMoviesUrl = "https://api.themoviedb.org/3/discover/movie?api_key=310e2ef8d44e911293fa5437f251d6c3&language=en-US&" +
            "sort_by=vote_average.desc&include_adult=false&include_video=false&page=1";
    String mRequestedMoviesUrl = "https://api.themoviedb.org/3/discover/movie?api_key=310e2ef8d44e911293fa5437f251d6c3&language=en-US&" +
            "sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);


        mRecyclerView = findViewById(R.id.recycler_view);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);


        loadMovies();

    }

    public void loadMovies() {
        new fetchTheMovie().execute();
    }

    @Override
    public void OnClick(View view, int position1, String flag) {

        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_POSITION, position1);
        intent.putExtra(MovieDetail.EXTRA_FLAG, flag);
        startActivity(intent);

    }

    @Override
    public void OnClick() {

    }


    public class fetchTheMovie extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(String... voids) {


            ArrayList<String> mP = mNetworkUtils.fetchMovieData(mRequestedMoviesUrl);


            return mP;
        }

        @Override
        protected void onPostExecute(ArrayList<String> v) {
            super.onPostExecute(v);
            mProgressBar.setVisibility(View.INVISIBLE);

            if (v != null) {
                mMovieAdapter.setMoviePosters(v);
            }


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        mRecyclerView.setVisibility(View.INVISIBLE);

        int id = item.getItemId();


        if (id == R.id.popular_menu_item) {

            mRequestedMoviesUrl = mPopularMoviesUrl;
            loadMovies();
        }

        if (id == R.id.top_rated_menu_item) {
            mRequestedMoviesUrl = mTopRatedMoviesUrl;
            loadMovies();
        }

        if (id == R.id.refresh) {
            loadMovies();
        }

        if (id == R.id.favorite_movies_item) {
            Intent intent = new Intent(this, FavoriteMovies.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

