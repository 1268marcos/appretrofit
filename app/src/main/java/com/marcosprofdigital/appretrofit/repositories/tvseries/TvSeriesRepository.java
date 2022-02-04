package com.marcosprofdigital.appretrofit.repositories.tvseries;
// 08.2
import androidx.lifecycle.LiveData;

import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.request.tvseries.TvSeriesApiClient;

import java.util.List;

public class TvSeriesRepository {
    // This class is acting as repositories
    private static TvSeriesRepository instance;

    private TvSeriesApiClient mTvSeriesApiClient;

    // trigger for use with RecyclerView - list of tvSeries then more 1 pages
    private String mQueryNextPage;
    private String mQueryPreviousPage; // not used - example for comments
    private int mPageNumberNextPage;
    private int mPageNumberPreviousPage; // not used - example for comments

    public static TvSeriesRepository getInstance(){
        // Java Singleton = permite criar objetos únicos para os quais há apenas uma instância.
        // https://refactoring.guru/pt-br/design-patterns/singleton/java/example
        // https://www.devmedia.com.br/padrao-de-projeto-singleton-em-java/26392
        if(instance == null){
            instance = new TvSeriesRepository();
        }
        return instance;
    }

    private TvSeriesRepository(){mTvSeriesApiClient = TvSeriesApiClient.getInstance();}

    public LiveData<List<TvSeriesModel>> getTvSeries(){ return mTvSeriesApiClient.getTvSeries(); }

    public LiveData<List<TvSeriesModel>> getTvSeriesPop(){ return mTvSeriesApiClient.getTvSeriesPop(); }

    // II - Calling the method in repository
    public void searchTvSeriesApi(String query, int pageNumber){
        mQueryNextPage = query;
        mQueryPreviousPage = query; // not used - example for comments
        mPageNumberNextPage = pageNumber;
        mTvSeriesApiClient.searchTvSeriesApi(query, pageNumber);
    }

    public void searchTvSeriesPop(int pageNumber){
        mPageNumberNextPage = pageNumber;
        mTvSeriesApiClient.searchTvSeriesPop(pageNumber);
    }

    public void searchNextPage(){
        searchTvSeriesApi(mQueryNextPage, mPageNumberNextPage+1);
    }

    public void searchPreviousPage(){ // not used - example for comments
        if(mPageNumberPreviousPage == 0){
            mPageNumberPreviousPage=1;
        }
        searchTvSeriesApi(mQueryPreviousPage, mPageNumberPreviousPage-1);
    }

}

