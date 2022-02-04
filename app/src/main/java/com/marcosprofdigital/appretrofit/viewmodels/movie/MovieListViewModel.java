package com.marcosprofdigital.appretrofit.viewmodels.movie;
// 09.1
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.repositories.movie.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository mMovieRepository;

    public MovieListViewModel(){
        mMovieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return mMovieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPopMovies(){
        return mMovieRepository.getMoviesPop();
    }

    public LiveData<List<MovieModel>> getPopPastMovies(){
        return mMovieRepository.getMoviesPopPast();
    }

    public void searchMovieApi(String query, int pageNumber){
        mMovieRepository.searchMovie(query, pageNumber);
    }

    public void searchMovieApiPop(int pageNumber){
        mMovieRepository.searchMoviePop(pageNumber);
    }

    public void searchMovieApiPopPast(int year, int pageNumber, String language){
        mMovieRepository.searchMoviePopPast(year, pageNumber, language);
    }

    public void searchNextPage(){
        mMovieRepository.searchNextPage();
    }

}
