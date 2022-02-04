package com.marcosprofdigital.appretrofit.views.tvseries;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.marcosprofdigital.appretrofit.R;
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.utils.Constants;

// 16.2
public class TvSeriesDetails  extends AppCompatActivity {

    // Widgets
    private ImageView mImageViewTvSeriesDetails;
    private TextView mTextViewTitleTvSeriesDetails, mTextViewDescTvSeriesDetails;
    private RatingBar mRatingBarTvSeriesDetails;

    private void getDataFromIntent(){
        if(getIntent().hasExtra("tvSeries")){
            TvSeriesModel mTvSeriesModel = getIntent().getParcelableExtra("tvSeries");
            mTextViewTitleTvSeriesDetails.setText(mTvSeriesModel.getName());
            mTextViewDescTvSeriesDetails.setText(mTvSeriesModel.getOverview());
            mRatingBarTvSeriesDetails.setRating((mTvSeriesModel.getVote_average())/2);
            Glide.with(this)
                    .load(Constants.IMAGE_URL+mTvSeriesModel.getPoster_path())
                    .into(mImageViewTvSeriesDetails);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvseries_details);
        mImageViewTvSeriesDetails = findViewById(R.id.imageView_tvSeries_details);
        mTextViewTitleTvSeriesDetails = findViewById(R.id.textView_title_tvSeries_details);
        mTextViewDescTvSeriesDetails = findViewById(R.id.textView_desc_tvSeries_details);
        mRatingBarTvSeriesDetails = findViewById(R.id.ratingBar_tvSeries_details);
        getDataFromIntent();
    }

}
