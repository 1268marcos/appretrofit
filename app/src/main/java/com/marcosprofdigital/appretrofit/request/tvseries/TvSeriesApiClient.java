package com.marcosprofdigital.appretrofit.request.tvseries;

// 07-B.2
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marcosprofdigital.appretrofit.extras.AppExecutors;
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.request.ServiceRetrofit;
import com.marcosprofdigital.appretrofit.response.tvseries.TvSeriesSearchResponse;
import com.marcosprofdigital.appretrofit.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class TvSeriesApiClient {

    private MutableLiveData<List<TvSeriesModel>> mTvSeries;
    private static TvSeriesApiClient instance;

    // Retrieving data from RestApi by runnable call
    // We have 2 types of Queries: the ID & search Queries
    // create subclass - Making global RUNNABLE
    private class RetrieveTvSeriesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        public RetrieveTvSeriesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        // Search method query
        private Call<TvSeriesSearchResponse> getTvSeries(String query, int pageNumber){
            return ServiceRetrofit.getTvSeriesApi().searchTvSeries(
                    Constants.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest(){  //setCancelRequest
            Log.v("Tag", "Canceling Search Request");
            cancelRequest = true;
        }

        @Override
        public void run() {
            // Getting the response objects
            try{
                Response response = getTvSeries(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<TvSeriesModel> list = new ArrayList<>(((TvSeriesSearchResponse)response.body()).getTvSeriesModelList());
                    if(pageNumber == 1) {
                        mTvSeries.postValue(list);
                    } else{
                        List<TvSeriesModel> currentTvSeries = mTvSeries.getValue();
                        currentTvSeries.addAll(list);
                        mTvSeries.postValue(currentTvSeries);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error run query is null TvSeriesApiClient.java-RetrieveTvSeriesRunnable: " + error);
                    mTvSeries.postValue(null);
                }

            } catch (IOException e){
                e.printStackTrace();
                mTvSeries.postValue(null);
            }
            if(cancelRequest){
                return;
            }
        }
    }

    private RetrieveTvSeriesRunnable mRetrieveTvSeriesRunnable;

    // LiveData for popular tvSeries
    private MutableLiveData<List<TvSeriesModel>> mTvSeriesPop;

    // Making popular RUNNABLE

    private class RetrieveTvSeriesRunnablePop implements Runnable{
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveTvSeriesRunnablePop(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        // Search method query
        private Call<TvSeriesSearchResponse> getTvSeriesPop(int pageNumber){
            return ServiceRetrofit.getTvSeriesApi().getPopular(
                    Constants.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){ //setCancelRequest
            Log.v("Tag", "Canceling Search Request");
            cancelRequest = true;
        }

        @Override
        public void run() {
            // Getting the response objects
            try{
                // package retrofit2.Response
                // https://square.github.io/retrofit/2.x/retrofit/retrofit2/class-use/Response.html
                Response responsePop = getTvSeriesPop(pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(responsePop.code() == 200){
                    List<TvSeriesModel> list = new ArrayList<>(((TvSeriesSearchResponse)responsePop.body()).getTvSeriesModelList());
                    if(pageNumber == 1){
                        // Sending data to LiveData .postValue: used for background thread
                        // Difference                .setValue: not for background thread
                        mTvSeriesPop.postValue(list);
                    } else{
                        List<TvSeriesModel> currentTvSeries = mTvSeriesPop.getValue();
                        currentTvSeries.addAll(list);
                        mTvSeriesPop.postValue(currentTvSeries);
                    }
                }else{
                    String error = responsePop.errorBody().string();
                    Log.v("Tag", "Error run RetrieveTvSeriesRunnablePop: " +error);
                    mTvSeriesPop.postValue(null);
                }
            }catch (IOException e){
                e.printStackTrace();
                mTvSeriesPop.postValue(null);
            }
            if(cancelRequest){
                return;
            }
        }
    }

    private RetrieveTvSeriesRunnablePop mRetrieveTvSeriesRunnablePop;

    public static TvSeriesApiClient getInstance(){
        if(instance == null){
            instance = new TvSeriesApiClient();
        }
        return instance;
    }

    // Constructor
    private TvSeriesApiClient(){
        mTvSeries = new MutableLiveData<>();
        mTvSeriesPop = new MutableLiveData<>();
    }

    public LiveData<List<TvSeriesModel>> getTvSeries(){return mTvSeries;}

    public LiveData<List<TvSeriesModel>> getTvSeriesPop(){return mTvSeriesPop;}

    // 1- This method that we are going to call through the classes
    public void searchTvSeriesApi(String query, int pageNumber){
        if(mRetrieveTvSeriesRunnable != null){
            mRetrieveTvSeriesRunnable = null;
        }
        mRetrieveTvSeriesRunnable = new RetrieveTvSeriesRunnable(query, pageNumber);
        // Future - trabalhar com chamadas assíncronas e processamento simultâneo.
        // https://www.baeldung.com/java-future
        // representa um resultado futuro de uma computação assíncrona. Esse resultado
        // eventualmente aparecerá no futuro após a conclusão do processamento.
        final Future myHandler = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveTvSeriesRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Timeout to cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void searchTvSeriesPop(int pageNumber){
        if(mRetrieveTvSeriesRunnablePop != null){
            mRetrieveTvSeriesRunnablePop = null;
        }
        mRetrieveTvSeriesRunnablePop = new RetrieveTvSeriesRunnablePop(pageNumber);
        final Future myHandlerPop = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveTvSeriesRunnablePop);
        // https://www.tabnine.com/code/java/classes/com.example.android.todolist.AppExecutors
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Timeout to cancelling the retrofit call
                myHandlerPop.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

}

