package com.alkhalilbg.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alkhalilbg.moviesapp.model.Moviee;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    List<Moviee> mMovies;
    private FavoriteMoviesAdapterOnClickHandler mClickHandler;


    public interface FavoriteMoviesAdapterOnClickHandler {
        void onClick(int itemId, int position, String flag);
    }

    public FavoriteMovieAdapter(FavoriteMoviesAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView movieNameTextView;


        public FavoriteMovieViewHolder(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.favorite_movie_iv);
            movieNameTextView = itemView.findViewById(R.id.favorite_movie_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemId = mMovies.get(getAdapterPosition()).getDatabaseItemId();
                    int position = getAdapterPosition();
                    mClickHandler.onClick(itemId, position, "B");
                }
            });
        }


        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_movie_item, parent, false);
        return new FavoriteMovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(FavoriteMovieViewHolder holder, int position) {

        Moviee movie = mMovies.get(position);
        String moviePoster = movie.getMoviePoster();
        String originalTitle = movie.getOriginalTitle();

        Picasso.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w185" + moviePoster).into(holder.imageView);
        holder.movieNameTextView.setText(originalTitle);

    }

    @Override
    public int getItemCount() {
        if (mMovies != null) {
            return mMovies.size();
        } else {
            return 0;
        }
    }

    public void setMovies(List<Moviee> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

}
