package com.alkhalilbg.moviesapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie", indices = @Index(value = {"movieId"}, unique = true))
public class Moviee {

    String mMoviePoster;
    String mOriginalTitle;
    String mOverview;
    Double mVoteAverage;
    String mReleaseDate;
    String mTrailerKey;
    @ColumnInfo(name = "movieId")
    int mMovieId;
    @PrimaryKey(autoGenerate = true)
    int mDatabaseItemId;

    @Ignore
    public Moviee() {

    }

    @Ignore
    public Moviee(String originalTitle, String overview, String releaseDate, Double voteAverage,
                  String moviePoster, int movieId) {

        this.mOriginalTitle = originalTitle;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mVoteAverage = voteAverage;
        this.mMoviePoster = moviePoster;
        this.mMovieId = movieId;

    }

    @Ignore
    public Moviee(String trailerKey) {

        this.mTrailerKey = trailerKey;

    }

    public Moviee(String originalTitle, String overview, String releaseDate, Double voteAverage,
                  String moviePoster, int movieId, int databaseItemId) {

        this.mOriginalTitle = originalTitle;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mVoteAverage = voteAverage;
        this.mMoviePoster = moviePoster;
        this.mMovieId = movieId;
        this.mDatabaseItemId = databaseItemId;

    }

    public String getMoviePoster() {
        return mMoviePoster;
    }

    public String setMoviePoster(String moviePoster) {
        mMoviePoster = moviePoster;

        return mMoviePoster;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }


    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }

    public void setTrailerKey(String trailerKey) {
        mTrailerKey = trailerKey;
    }

    public int getDatabaseItemId() {
        return mDatabaseItemId;
    }

    public void setDatabaseItemId(int databaseItemId) {
        mDatabaseItemId = databaseItemId;
    }
}

