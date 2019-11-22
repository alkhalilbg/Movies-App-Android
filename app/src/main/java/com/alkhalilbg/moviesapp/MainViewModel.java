package com.alkhalilbg.moviesapp;

import android.app.Application;

import com.alkhalilbg.moviesapp.database.AppDatabase;
import com.alkhalilbg.moviesapp.model.Moviee;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    LiveData<List<Moviee>> mMovies;

    public MainViewModel(Application application) {
        super(application);

        mMovies = AppDatabase.getInstance(getApplication()).movieDao().loadAllMovies();

    }

    public LiveData<List<Moviee>> getMovies() {
        return mMovies;
    }
}
