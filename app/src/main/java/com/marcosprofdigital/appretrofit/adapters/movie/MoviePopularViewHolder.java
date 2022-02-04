package com.marcosprofdigital.appretrofit.adapters.movie;
// 14
import com.marcosprofdigital.appretrofit.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviePopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Widgets
    ImageView mImageViewPop;
    RatingBar mRatingBarPop;

    // Click Listener
    OnMovieListener mOnMovieListener;

    public MoviePopularViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);
        mOnMovieListener = onMovieListener;

        mImageViewPop = itemView.findViewById(R.id.imageView_pop_movie);
        mRatingBarPop = itemView.findViewById(R.id.ratingBar_pop_movie);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mOnMovieListener.onMovieClick(getLayoutPosition());
    }
}
