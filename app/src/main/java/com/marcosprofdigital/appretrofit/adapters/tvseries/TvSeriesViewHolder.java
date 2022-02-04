package com.marcosprofdigital.appretrofit.adapters.tvseries;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.marcosprofdigital.appretrofit.R;

// 13.2
public class TvSeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // Widgets in layout     movie_list_item.xml
    TextView mTextViewTitleTvSeries, mTextViewDurationTvSeries, mTextViewReleaseDateTvSeries, mTextViewLanguageTvSeries;
    ImageView mImageViewTvSeries;
    RatingBar mRatingBarTvSeries;

    // Click Listener
    OnTvSeriesListener mOnTvSeriesListener;

    public TvSeriesViewHolder(@NonNull View itemView, OnTvSeriesListener onTvSeriesListener) {
        super(itemView);

        this.mOnTvSeriesListener = onTvSeriesListener;

        mTextViewDurationTvSeries = itemView.findViewById(R.id.textView_duration_tvSeries);
        mTextViewLanguageTvSeries = itemView.findViewById(R.id.textView_language_tvSeries);
        mTextViewReleaseDateTvSeries = itemView.findViewById(R.id.textView_release_date_tvSeries);
        mTextViewTitleTvSeries = itemView.findViewById(R.id.textView_title_tvSeries);

        mImageViewTvSeries = itemView.findViewById(R.id.imageView_tvSeries);

        mRatingBarTvSeries = itemView.findViewById(R.id.ratingBar_tvSeries);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        mOnTvSeriesListener.onTvSeriesClick(getBindingAdapterPosition());
    }
}
