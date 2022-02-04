package com.marcosprofdigital.appretrofit.viewmodels.tvseries;
// 09.2
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.repositories.tvseries.TvSeriesRepository;

import java.util.List;

public class TvSeriesListViewModel extends ViewModel {

    // this class is used for ViewModel (MVVM)

    private TvSeriesRepository mTvSeriesRepository;

    // constructor
    public TvSeriesListViewModel() {mTvSeriesRepository = TvSeriesRepository.getInstance(); }

    public LiveData<List<TvSeriesModel>> getTvSeries(){return  mTvSeriesRepository.getTvSeries(); }
    public LiveData<List<TvSeriesModel>> getPopTvSeries(){
        return mTvSeriesRepository.getTvSeriesPop();
    }

    // III - Calling method in ViewModel in MVVM
    public void searchTvSeriesApi(String query, int pageNumber){
        mTvSeriesRepository.searchTvSeriesApi(query, pageNumber);
    }

    public void searchTvSeriesPop(int pageNumber){
        mTvSeriesRepository.searchTvSeriesPop(pageNumber);
    }

    public void searchNextPage(){
        mTvSeriesRepository.searchNextPage();
    }

}
