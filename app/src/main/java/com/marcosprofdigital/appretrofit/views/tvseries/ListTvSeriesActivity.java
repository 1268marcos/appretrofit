package com.marcosprofdigital.appretrofit.views.tvseries;
// 17.2
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.marcosprofdigital.appretrofit.R;
import com.marcosprofdigital.appretrofit.adapters.tvseries.OnTvSeriesListener;
import com.marcosprofdigital.appretrofit.adapters.tvseries.TvSeriesRecyclerView;
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.viewmodels.tvseries.TvSeriesListViewModel;

import java.util.List;

public class ListTvSeriesActivity extends AppCompatActivity implements OnTvSeriesListener {

    private TextView mTextViewHeader;

    private BottomNavigationView mBottomNavigationView;
    private RecyclerView mRecyclerView;
    private TvSeriesRecyclerView mTvSeriesRecyclerViewAdapter;
    private TvSeriesListViewModel mTvSeriesListViewModel;
    boolean isPopularTvSeries = true;

    // To observe any data change
    private void observeAnyChange(){
        mTvSeriesListViewModel.getTvSeries().observe(this, new Observer<List<TvSeriesModel>>() {
            @Override
            public void onChanged(List<TvSeriesModel> tvSeriesModels) {
                // Observing any data change - recyclerview
                Log.v("Tag", "onChanged: see time in observeAnyChang()");
                if(tvSeriesModels != null){
                    for(TvSeriesModel tvSeriesModel : tvSeriesModels){  // TEST THIS ???
                        // Get the data in log
                        Log.v("Tag", "onChanged-if-for: id "+ tvSeriesModel.getTvSeries_id() + " Original Name " + tvSeriesModel.getOriginal_name());
                        mTvSeriesRecyclerViewAdapter.setTvSeries(tvSeriesModels); // TEST THIS ??
                    }
                }
            }
        });
    }

    int j = 0;
    private void observePopularTvSeries(){
        mTvSeriesListViewModel.getPopTvSeries().observe(this, new Observer<List<TvSeriesModel>>() {
            @Override
            public void onChanged(List<TvSeriesModel> tvSeriesModels) {
                // Observing any data change - recyclerview
                Log.v("Tag", "onChanged: see time in observePopularTvSeries()");
                if(tvSeriesModels != null){
                    for(TvSeriesModel tvSeriesModel : tvSeriesModels){  // TEST THIS ???
                        // Get the data in log
                        j++;
                        Log.v("Tag", "onChanged-if-for: id " + j + " " + tvSeriesModel.getTvSeries_id() + " Original Name " + tvSeriesModel.getOriginal_name());
                        mTvSeriesRecyclerViewAdapter.setTvSeries(tvSeriesModels); // TEST THIS ???
                    }
                }
            }
        });
    }

    // 4 - Calling method in (Main) Activity
    private void searchTvSeriesApi(String query, int pageNumber){
        mTvSeriesListViewModel.searchTvSeriesApi(query, pageNumber);
    }

    // 5 - (3:09:50) Initializing recyclerView & adding data to it
    private void setupConfigureRecyclerView(){
        // LiveData cant be passed via the constructor
        mTvSeriesRecyclerViewAdapter = new TvSeriesRecyclerView(this);
        mRecyclerView.setAdapter(mTvSeriesRecyclerViewAdapter);
        // CardView Horizontal - Carousel
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true); // Marcos
        // RecyclerView Pagination
        // Load next page of api response
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                // super.onScrollStateChanged(recyclerView, newState);
                if(!mRecyclerView.canScrollVertically(1)){
                    // Here we need to display the next search results on the next page of api
                    // trigger in MovieRepository
                    mTvSeriesListViewModel.searchNextPage();
                }
            }
        });
    }

    public void configureVisibilityBottomNavView(String s){
        if(s.isEmpty()){
            mBottomNavigationView.setVisibility(View.VISIBLE);
        } else{
            mBottomNavigationView.setVisibility(View.GONE);
        }
    }

    // Get data from search view & query the api to get the results
    private void setupSearchView(){
        final SearchView mSearchView = findViewById(R.id.search_view); // activity_main.xml
        // important CLEAN Architecture
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mTvSeriesListViewModel.searchTvSeriesApi(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                configureVisibilityBottomNavView(s);
                return false;
            }
        });
        mSearchView.setOnSearchClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //isPopularTvSeries = false; // cause app anomaly
                isPopularTvSeries = true;
            }
        });
    }

    @Override
    public void onTvSeriesClick(int position) {
        Intent intent = new Intent(this, TvSeriesDetails.class);
        intent.putExtra("tvSeries", mTvSeriesRecyclerViewAdapter.getSelectedTvSeries(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryTvSeries(String category) {

    }

    public class ClickButton implements View.OnClickListener{
        @Override
        public void onClick(View view){
            searchTvSeriesApi("slow", 1);
        }
    }

    public class ClickItemBottomNavigationView implements NavigationBarView.OnItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.item_popular: observePopularTvSeries(); searchTvSeriesApi(null,1);return true;
                case R.id.item_popular_past: observeAnyChange(); searchTvSeriesApi("dark", 1);return true;
                case R.id.item_random: observeAnyChange(); searchTvSeriesApi("friends", 1);return true;
                case R.id.item_trending: observeAnyChange(); searchTvSeriesApi("better",1);return true;
            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewHeader = findViewById(R.id.textView_header);
        mTextViewHeader.setText("Escolha uma série para hoje");

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnItemSelectedListener(new ClickItemBottomNavigationView());

        // ToolBar - import androidx.appcompat.widget.Toolbar;
        Toolbar mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        // SearchView
        setupSearchView();

        mTvSeriesListViewModel = new ViewModelProvider(this).get(TvSeriesListViewModel.class);

        mRecyclerView = findViewById(R.id.recyclerView);
        setupConfigureRecyclerView();
        //observeAnyChange();
        observePopularTvSeries();

        mTvSeriesListViewModel.searchTvSeriesPop(1);


    }

}

