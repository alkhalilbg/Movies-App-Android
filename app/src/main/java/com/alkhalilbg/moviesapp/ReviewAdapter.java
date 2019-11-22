package com.alkhalilbg.moviesapp;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    ArrayList<String> mContentList;

    ReviewAdapterClickHandler mClickHandler;


    public interface ReviewAdapterClickHandler {
        void onClick(View view, int position, int movieId);
    }

    public ReviewAdapter(ReviewAdapterClickHandler clickHandler) {

        this.mClickHandler = clickHandler;
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView reviewTextView;

        public ReviewViewHolder(View view) {
            super(view);

            reviewTextView = view.findViewById(R.id.review_tv);

        }

        @Override
        public void onClick(View v) {

        }
    }

    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);

        return new ReviewViewHolder(view);
    }

    public void onBindViewHolder(ReviewViewHolder holder, int position) {

        String review = String.valueOf(mContentList.get(position));
        holder.reviewTextView.setText(review);
        Log.d("k", "review: " + review);
    }

    @Override
    public int getItemCount() {

        if (mContentList == null) return 0;
        return mContentList.size();

    }

    public void setReviews(ArrayList<String> contentList) {
        mContentList = contentList;
        notifyDataSetChanged();
    }
}
