package com.alkhalilbg.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    ArrayList<String> mMoviePosters;
    MovieAdapterOnClickHandler mClickHandler;
    Context mContext;

    public interface MovieAdapterOnClickHandler {

        void OnClick(View view, int position, String flag);

        void OnClick();

    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {

        mClickHandler = clickHandler;

    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.movie_poster_iv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickHandler.OnClick(v, getAdapterPosition(), "A");
                }
            });

            mContext = itemView.getContext();

        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int itemLayout = R.layout.item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(itemLayout, viewGroup, false);

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String moviePoster = String.valueOf(mMoviePosters.get(position));

        Picasso.with(movieAdapterViewHolder.itemView.getContext()).load("https://image.tmdb.org/t/p/w185" + moviePoster)
                .into(movieAdapterViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviePosters) return 0;
        return mMoviePosters.size();

    }

    public void setMoviePosters(ArrayList<String> moviePosters) {
        mMoviePosters = moviePosters;
        notifyDataSetChanged();
    }
}
