package com.marcosprofdigital.appretrofit.views.movie;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.marcosprofdigital.appretrofit.R;
import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.utils.Constants;

public class MovieDetails extends AppCompatActivity {

    // Widgets - Objects
    private ImageView mImageViewDetails;
    private TextView mTextViewTitleDetails, mTextViewDescDetails;
    private RatingBar mRatingBarDetails;

    private void getDataFromIntent(){
        if(getIntent().hasExtra("movie")){
            MovieModel mMovieModel = getIntent().getParcelableExtra("movie");
            mTextViewTitleDetails.setText(mMovieModel.getTitle());
            mTextViewDescDetails.setText(mMovieModel.getMovie_overview());
            mRatingBarDetails.setRating(mMovieModel.getVote_average()/2);

            Glide.with(this)
                    .load(Constants.IMAGE_URL + mMovieModel.getPoster_path())
                    .into(mImageViewDetails);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);

        mImageViewDetails = findViewById(R.id.imageView_movie_details);
        mTextViewTitleDetails = findViewById(R.id.textView_title_movie_details);
        mTextViewDescDetails = findViewById(R.id.textView_desc_movie_details1);
        mRatingBarDetails = findViewById(R.id.rattingBar_movie_details);

        getDataFromIntent();

    }

}
