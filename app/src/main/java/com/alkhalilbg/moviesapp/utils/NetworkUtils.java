package com.alkhalilbg.moviesapp.utils;


import com.alkhalilbg.moviesapp.model.Moviee;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class NetworkUtils {


    public static String data;
    public static JSONArray resultsArray;
    public static JSONObject movieInfo;


    public ArrayList<String> fetchMovieData(String url) {

        ArrayList<String> moviePosters = new ArrayList<>();


        try {
            URL requestedUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestedUrl.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            data = IOUtils.toString(inputStream, (Charset) null);

            JSONObject movieJsonObject = null;
            try {
                movieJsonObject = new JSONObject(data);

                resultsArray = movieJsonObject.getJSONArray("results");
                movieInfo = resultsArray.getJSONObject(0);

                String moviePosterUrl = null;

                for (int i = 0; i < resultsArray.length(); i++) {

                    JSONObject movie1 = resultsArray.getJSONObject(i);
                    moviePosterUrl = movie1.getString("poster_path");
                    moviePosters.add(moviePosterUrl);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            paresJson(movieInfo);

            inputStream.close();

            return moviePosters;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public Moviee paresJson(JSONObject movieInfo) {

        String originalTitle = null;
        String overview = null;
        String releaseDate = null;
        Double vote_average = null;
        String posterUrl = null;
        int movieId = 0;
        String trailerData;


        try {

            JSONObject movie = movieInfo;
            originalTitle = movie.getString("original_title");
            overview = movie.getString("overview");
            releaseDate = movie.getString("release_date");
            vote_average = movie.getDouble("vote_average");
            posterUrl = movie.getString("poster_path");
            movieId = movie.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();

        }


        return new Moviee(originalTitle, overview, releaseDate, vote_average, posterUrl, movieId);


    }

}
