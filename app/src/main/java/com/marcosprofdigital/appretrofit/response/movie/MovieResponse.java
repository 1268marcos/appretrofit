package com.marcosprofdigital.appretrofit.response.movie;
// 03.1
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marcosprofdigital.appretrofit.models.movie.MovieModel;

public class MovieResponse {

    @SerializedName("results")
    @Expose
    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movie=" + movie +
                "}";
    }
}
