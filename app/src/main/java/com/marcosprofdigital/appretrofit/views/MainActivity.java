package com.marcosprofdigital.appretrofit.views;
// 0
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.marcosprofdigital.appretrofit.views.movie.ListMovieActivity;
import com.marcosprofdigital.appretrofit.views.tvseries.ListTvSeriesActivity;
import com.marcosprofdigital.appretrofit.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout mLinearLayoutMovie, mLinearLayoutTvSeries, mLinearLayoutStreaming, mLinearLayoutRent, mLinearLayoutCredits;

    TextView mTextViewDashboardCredits;

    private void showMovie(){
        Intent intent = new Intent(this, ListMovieActivity.class);
        startActivity(intent);
    }

    private void showTvSeries(){
        Intent intent = new Intent(this, ListTvSeriesActivity.class);
        startActivity(intent);
    }

    private void showStreaming(){}

    private void showRent(){}

    private void showCredits(){}

    public class MovieClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            showMovie();
        }
    }

    public class TvSeriesClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            showTvSeries();
        }
    }

    public void showVersion(){
//        String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
//        String versionName = String.valueOf(BuildConfig.VERSION_NAME);
//        mTextViewDashboardCredits.setText(versionName);
        //https://stackoverflow.com/questions/4616095/how-can-you-get-the-build-version-number-of-your-android-application

        PackageInfo pInfo = null; //2021.12.30
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionNumber = pInfo.versionCode;
        String versionName = pInfo.versionName;
        mTextViewDashboardCredits.setText(versionName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        mLinearLayoutMovie = findViewById(R.id.layout_movie);
        mLinearLayoutMovie.setOnClickListener(new MovieClick());

        mLinearLayoutTvSeries = findViewById(R.id.layout_tvSeries);
        mLinearLayoutTvSeries.setOnClickListener(new TvSeriesClick());

        mTextViewDashboardCredits = findViewById(R.id.textView_dashboard_credits);
        showVersion();

    }
}
