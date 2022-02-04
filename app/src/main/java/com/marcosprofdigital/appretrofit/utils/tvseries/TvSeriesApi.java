package com.marcosprofdigital.appretrofit.utils.tvseries;
// 05.2
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.response.tvseries.TvSeriesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvSeriesApi {

    @GET("/3/search/tv")
    Call<TvSeriesSearchResponse> searchTvSeries(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("/3/tv/popular")
    Call<TvSeriesSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("/3/tv/{tvSeries_id}?")
    Call<TvSeriesModel> getTvSeries(
            @Path("tvSeries_id") int tvSeries_id,
            @Query("api_key") String api_key
    );


}
