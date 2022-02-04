package com.marcosprofdigital.appretrofit.response.tvseries;


// 03.2

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;

public class TvSeriesResponse {

    @SerializedName("results")
    @Expose
    private TvSeriesModel mTvSeries;

    public TvSeriesModel getTvSeries(){return  mTvSeries;}

    @Override
    public String toString(){
        return "TvSeriesResponse{" +
                "mTvSeries=" + mTvSeries +
                "}";
    }


}
