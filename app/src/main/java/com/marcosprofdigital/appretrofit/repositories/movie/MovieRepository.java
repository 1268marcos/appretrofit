package com.marcosprofdigital.appretrofit.repositories.movie;
// 08
import androidx.lifecycle.LiveData;

import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.request.movie.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient mMovieApiClient;

    private int mYear;

    private String mLanguage;

    private String mQueryNextPage;

    private int mPageNumberNextPage;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        mMovieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return  mMovieApiClient.getMovies();
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return  mMovieApiClient.getMoviesPop();
    }

    public LiveData<List<MovieModel>> getMoviesPopPast(){
        return  mMovieApiClient.getMoviesPopPast();
    }

    public void searchMovie(String query, int pageNumber){
        mQueryNextPage = query;
        mPageNumberNextPage = pageNumber;
        mYear = 0; // 2022.01.31
        mMovieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber){
        mPageNumberNextPage = pageNumber;
        mYear = 0; // 2022.01.31
        mMovieApiClient.searchMoviesPop(pageNumber);
    }

    public void searchMoviePopPast(int year, int pageNumber, String language){
        mYear = year;
        mPageNumberNextPage = pageNumber;
        mLanguage = language;
        mQueryNextPage = null; // 2022.01.31
        mMovieApiClient.searchMoviesPopPast(year, pageNumber, language);
    }

    public void searchNextPage(){
        //searchMovie(mQueryNextPage, mPageNumberNextPage+1);
        if(mYear == 0 ){
            searchMovie(mQueryNextPage, mPageNumberNextPage+1);

        }else{
            searchMoviePopPast(mYear, mPageNumberNextPage+1, mLanguage);
        }

    }


}
