package com.marcosprofdigital.appretrofit.utils.movie;
// 05.1
import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.response.movie.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
         @Path("movie_id") int movie_id,
         @Query("api_key") String key
    );

    @GET("/3/discover/movie/")
    Call<MovieSearchResponse> getMoviePopularPast(
            @Query("primary_release_year") int year,
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("language") String language
    );

}
