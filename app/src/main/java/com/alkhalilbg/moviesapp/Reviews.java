package com.alkhalilbg.moviesapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Reviews extends AppCompatActivity implements ReviewAdapter.ReviewAdapterClickHandler {

    RecyclerView mReviewsRecyclerView;
    ReviewAdapter mReviewsAdapter;
    String mUrl;
    int mMovieId;

    public static final String EXTRA_ID = "extra_id";
    public static final int DEFAULT_ID = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list);

        mReviewsRecyclerView = findViewById(R.id.recycler_view_reviews);
        mReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReviewsAdapter = new ReviewAdapter(this);
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);

        mReviewsRecyclerView.setHasFixedSize(true);

        mReviewsRecyclerView.addItemDecoration(new DividerItemDecoration(mReviewsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Intent intent = getIntent();
        mMovieId = intent.getIntExtra(EXTRA_ID, DEFAULT_ID);

        mUrl = "https://api.themoviedb.org/3/movie/" + mMovieId + "/reviews?api_key=310e2ef8d44e911293fa5437f251d6c3";
        Log.d("k", "movie id: " + mMovieId);

        loadReviews();

    }

    public void loadReviews() {
        new reviewsAsyncTask().execute();
    }

    @Override
    public void onClick(View view, int position, int movieId) {

    }

    public class reviewsAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mReviewsRecyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(String... voids) {

            ArrayList<String> contentList = new ArrayList<>();

            try {


                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                String reviewsData = IOUtils.toString(inputStream, (Charset) null);
                JSONObject jsonObject = new JSONObject(reviewsData);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject review = results.getJSONObject(i);
                    String content = review.getString("content");
                    contentList.add(content);
                }
            } catch (Exception e) {

            }
            return contentList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> contentList) {
            super.onPostExecute(contentList);
            if (contentList != null) {
                mReviewsAdapter.setReviews(contentList);
            }

        }
    }
}

