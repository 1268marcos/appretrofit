package com.marcosprofdigital.appretrofit.adapters.movie;
// 13
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marcosprofdigital.appretrofit.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mTextViewTitle, mTextViewDuration, mTextViewReleaseDate, mTextViewLanguage;
    ImageView mImageViewMovie;
    RatingBar mRatingBar;

    OnMovieListener mOnMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.mOnMovieListener = onMovieListener;

        mTextViewTitle = itemView.findViewById(R.id.textView_movie_title);

        mTextViewReleaseDate = itemView.findViewById(R.id.textView_movie_release_date);

        mTextViewDuration = itemView.findViewById(R.id.textView_movie_duration);
        mTextViewLanguage = itemView.findViewById(R.id.textView_language_movie);
        mImageViewMovie = itemView.findViewById(R.id.imageView_movie);
        mRatingBar = itemView.findViewById(R.id.rattingBar_movie);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mOnMovieListener.onMovieClick(getBindingAdapterPosition());
    }
}
