package com.alkhalilbg.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alkhalilbg.moviesapp.database.AppDatabase;
import com.alkhalilbg.moviesapp.model.Moviee;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteMovies extends AppCompatActivity implements FavoriteMovieAdapter.FavoriteMoviesAdapterOnClickHandler {

    RecyclerView mRecyclerView;
    FavoriteMovieAdapter mAdapter;
    AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_movie_list);

        mRecyclerView = findViewById(R.id.recycler_view_favorite);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FavoriteMovieAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), 1);

        mRecyclerView.addItemDecoration(decoration);

        setupViewModel();
    }

    public void setupViewModel() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, new Observer<List<Moviee>>() {
            @Override
            public void onChanged(@Nullable List<Moviee> movies) {
                mAdapter.setMovies(movies);
            }
        });
    }


    @Override
    public void onClick(int itemId, int position2, String flag) {

        Intent intent = new Intent(this, FavoriteMovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_ID, itemId);
        intent.putExtra(MovieDetail.EXTRA_POSITION, position2);
        intent.putExtra(MovieDetail.EXTRA_FLAG, flag);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_from_db_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.refresh_item) {
            setupViewModel();
        }

        return super.onOptionsItemSelected(item);
    }
}
