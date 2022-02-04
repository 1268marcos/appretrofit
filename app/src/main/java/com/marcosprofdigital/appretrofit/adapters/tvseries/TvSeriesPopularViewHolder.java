package com.marcosprofdigital.appretrofit.adapters.tvseries;


import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marcosprofdigital.appretrofit.R;

// 14.2
public class TvSeriesPopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // Widgets
    ImageView mImageViewTvSeriesPop;
    RatingBar mRatingBarTvSeriesPop;

    // Click Listener
    OnTvSeriesListener mOnTvSeriesListener;

    public TvSeriesPopularViewHolder(@NonNull View itemView, OnTvSeriesListener onTvSeriesListener) {
        super(itemView);
        mOnTvSeriesListener =onTvSeriesListener;

        mImageViewTvSeriesPop = itemView.findViewById(R.id.imageView_pop_tvSeries);
        mRatingBarTvSeriesPop = itemView.findViewById(R.id.ratingBar_pop_tvSeries);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // Display the details on later videos
        // onTvSeriesClick INTERFACE OnTvSeriesListener
        // getLayoutPosition CLASS RecyclerView
        // https://www.instagram.com/p/CXP902elh8C/   CALLBACK
        // https://www.instagram.com/p/CWvlJ8sPpy4/
        mOnTvSeriesListener.onTvSeriesClick(getLayoutPosition()); //???
    }
}
