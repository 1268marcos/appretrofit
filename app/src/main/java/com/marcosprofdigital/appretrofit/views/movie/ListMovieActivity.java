package com.marcosprofdigital.appretrofit.views.movie;
// 17.1
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.marcosprofdigital.appretrofit.R;
import com.marcosprofdigital.appretrofit.adapters.movie.MovieRecyclerView;
import com.marcosprofdigital.appretrofit.adapters.movie.OnMovieListener;
import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.utils.Constants;
import com.marcosprofdigital.appretrofit.viewmodels.movie.MovieListViewModel;

import java.util.List;

public class ListMovieActivity extends AppCompatActivity implements OnMovieListener {

    private TextView mTextViewHeader;
    private BottomNavigationView mBottomNavigationView;

    private RecyclerView mRecyclerView;
    private MovieRecyclerView mMovieRecyclerViewAdapter;

    private MovieListViewModel mMovieListViewModel;

    boolean isPopular = true;

    private void observeAnyChange(){
        mMovieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag", "onChanged observeAnyChange ListMovieActivity");
                if(movieModels != null){
                    //adequar o movieModels
                    mMovieRecyclerViewAdapter.setMovies(movieModels);
                }
            }
        });
    }

    private void observePopularMovies(){
        mMovieListViewModel.getPopMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag", "onChanged observePopularMovies ListMovieActivity");
                if(movieModels != null){
                    //adequar o moviemodels
                    mMovieRecyclerViewAdapter.setMovies(movieModels);
                }
            }
        });
    }

    private void observePopularPastMovies(){
        mMovieListViewModel.getPopPastMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                Log.v("Tag", "onChanged observePopularPastMovies ListMovieActivity");
                if(movieModels != null){
                    //adequar o moviemodels
                    mMovieRecyclerViewAdapter.setMovies(movieModels);
                }
            }
        });
    }

    private void searchMovieApi(String query, int pageNumber){
        mMovieListViewModel.searchMovieApi(query, pageNumber);
    }

    private void searchMovieApiPopPast(int year, int pageNumber, String language){
        mMovieListViewModel.searchMovieApiPopPast(year, pageNumber, language);
    }

    private void setupConfigureRecyclerView(){
        mMovieRecyclerViewAdapter = new MovieRecyclerView(this);
        mRecyclerView.setAdapter(mMovieRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                //super.onScrollStateChanged(recyclerView, newState);
                if(!mRecyclerView.canScrollVertically(1)){
                    mMovieListViewModel.searchNextPage();
                }
            }
        });
    }

    public void configureVisibilityBottomNavItem(String s){
        if(s.isEmpty()){
            mBottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            mBottomNavigationView.setVisibility(View.GONE);
        }
    }

    private void setupSearchView(){
        final SearchView mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMovieListViewModel.searchMovieApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                configureVisibilityBottomNavItem(newText);
                return false;
            }
        });

        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = true;
            }
        });

    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", mMovieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    public class ClickButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            searchMovieApi("Homem Aranha", 1);
        }
    }

    public class ClickItemBottomNavigationView implements NavigationBarView.OnItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){

                case R.id.item_popular: observePopularMovies(); searchMovieApi(null, 1);return true;

                case R.id.item_popular_past: observePopularPastMovies(); searchMovieApiPopPast(1999, 1, Constants.LANGUAGE); return true;

                case R.id.item_random: observeAnyChange(); searchMovieApi("dark", 1); return true;

                case R.id.item_trending: observeAnyChange(); searchMovieApi("hulk", 1); return true;

            }
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTextViewHeader = findViewById(R.id.textView_header);
        mTextViewHeader.setText("Escolha para hoje");

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        mBottomNavigationView.setOnItemSelectedListener(new ClickItemBottomNavigationView());

        Toolbar mToolbar = findViewById(R.id.toolBar);
        //setSupportActionBar(mToolbar);

        setupSearchView();

        mMovieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        mRecyclerView = findViewById(R.id.recyclerView);
        setupConfigureRecyclerView();
        //observeAnyChange();
        // 2022.01.31
        observePopularMovies();
       // observePopularPastMovies();

        mMovieListViewModel.searchMovieApiPop(1);

    }

}
