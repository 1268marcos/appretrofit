package com.marcosprofdigital.appretrofit.request;
// 06
import com.marcosprofdigital.appretrofit.utils.Constants;
import com.marcosprofdigital.appretrofit.utils.movie.MovieApi;
import com.marcosprofdigital.appretrofit.utils.tvseries.TvSeriesApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRetrofit {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }

    private static TvSeriesApi tvSeriesApi = retrofit.create(TvSeriesApi.class);

    public static TvSeriesApi getTvSeriesApi() {
        return tvSeriesApi;
    }

}
