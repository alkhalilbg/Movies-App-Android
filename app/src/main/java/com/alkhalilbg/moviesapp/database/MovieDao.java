package com.alkhalilbg.moviesapp.database;


import com.alkhalilbg.moviesapp.model.Moviee;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface MovieDao {

    @Query("Select * From movie")
    LiveData<List<Moviee>> loadAllMovies();

    @Query("Select * From movie")
    List<Moviee> loadAllMovies2();


    @Insert(onConflict = 5)
    void insertMovie(Moviee movie);

    @Query("Select * From movie Where mDatabaseItemId = :id")
    LiveData<Moviee> loadMovieById(int id);

    @Query("Delete From movie Where mDatabaseItemId = :id")
    void deleteById(int id);


}

