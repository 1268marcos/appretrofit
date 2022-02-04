package com.marcosprofdigital.appretrofit.response.tvseries;


// 04.2

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;

import java.util.List;

public class TvSeriesSearchResponse {

    @SerializedName("total_results")
    @Expose()
    private int total_count_tv_series;

    @SerializedName("results")
    @Expose()
    private List<TvSeriesModel> tvSeriesModelList;

    public int getTotal_count_tv_series(){
        return total_count_tv_series;
    }

    public List<TvSeriesModel> getTvSeriesModelList(){return tvSeriesModelList;}


    @Override
    public String toString(){
        return "TvSeriesSearchResponse{" +
                "total_count_tv_series=" +
                total_count_tv_series +
                ", tvSeriesModelList=" +
                tvSeriesModelList +
                "}";
    }

}
