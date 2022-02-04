package com.marcosprofdigital.appretrofit.request.movie;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.marcosprofdigital.appretrofit.extras.AppExecutors;
import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.request.ServiceRetrofit;
import com.marcosprofdigital.appretrofit.response.movie.MovieSearchResponse;
import com.marcosprofdigital.appretrofit.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    // MVVM + LiveData

    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    public static MovieApiClient getInstance(){
        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies(){
        return  mMovies;
    }

    public LiveData<List<MovieModel>> getMoviesPop(){
        return mMoviesPop;
    }

    public LiveData<List<MovieModel>> getMoviesPopPast(){
        return mMoviesPopPast;
    }

    //Constructor 2022.01.28
    private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPop = new MutableLiveData<>();
        mMoviesPopPast = new MutableLiveData<>();
    }

    private MutableLiveData<List<MovieModel>> mMoviesPop;
    private MutableLiveData<List<MovieModel>> mMoviesPopPast;

    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            //2022.01.28
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMovies.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error run RetrieveMoviesRunnable MovieApiClient: " +error);
                    mMovies.postValue(null);
                }
            } catch (IOException e){
                e.printStackTrace();
                mMovies.postValue(null);
            }

            if(cancelRequest){
                return;
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return ServiceRetrofit.getMovieApi().searchMovie(
                    Constants.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Canceling Search Request");
            cancelRequest = true;
        }

    }

    private class RetrieveMoviesRunnablePop implements Runnable{
        private int pageNumber;
        boolean cancelRequest;
        public RetrieveMoviesRunnablePop(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }
        @Override
        public void run() {
            try{
                Response response2 = getPop(pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response2.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response2.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesPop.postValue(list);
                    }else{
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                }else{
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error run RetrieveMoviesRunnablePop MovieApiClient : " + error);
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e){
                e.printStackTrace();
                mMoviesPop.postValue(null);
            }

            if(cancelRequest){
                return;
            }
        }
        private Call<MovieSearchResponse> getPop(int pageNumber){
            return ServiceRetrofit.getMovieApi().getPopular(
                    Constants.API_KEY,
                    pageNumber
            );
        }

        //2022.01.28
        private void cancelRequest(){  //setCancelRequest
            Log.v("Tag", "Canceling Search Request");
            cancelRequest = true;
        }

    }

    private class RetrieveMoviesRunnablePopPast implements Runnable{
        private int mPageNumber;
        boolean mCancelRequest;
        private int mYear;
        private String mLanguage;

        public RetrieveMoviesRunnablePopPast(int year, int pageNumber, String language) {
            // 2022.01.28
            this.mPageNumber = pageNumber;
            mCancelRequest = false;
            this.mYear = year;
            this.mLanguage = language;
        }

        private Call<MovieSearchResponse> getMoviePopPast(int year, int pageNumber, String language){
            return ServiceRetrofit.getMovieApi().getMoviePopularPast(
                    year,
                    Constants.API_KEY,
                    pageNumber,
                    language
            );
        }

        private void cancelRequest(){
            Log.v("Tag", "Cancelling Search Request Movie Pop Past");
            mCancelRequest=true;
        }

        @Override
        public void run() {
            try{
                Response responsePopPast = getMoviePopPast(mYear, mPageNumber, mLanguage).execute();
                //2022.01.28
                if(mCancelRequest){
                    return;
                }
                if(responsePopPast.code()==200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)responsePopPast.body()).getMovies());
                    if(mPageNumber==1){
                        mMoviesPopPast.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMoviesPopPast.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopPast.postValue(currentMovies);
                    }
                } else {
                    String error = responsePopPast.errorBody().string();
                    Log.v("Tag", "Error run RetrieveMoviesRunnablePopPast MovieApiClient :" +error);
                    mMoviesPopPast.postValue(null);
                }
            }catch (IOException e){
                e.printStackTrace();
                mMoviesPopPast.postValue(null);
            }
            if(mCancelRequest){
                return;
            }
        }
    }

    private RetrieveMoviesRunnable mRetrieveMoviesRunnable;
    private RetrieveMoviesRunnablePop mRetrieveMoviesRunnablePop;
    private RetrieveMoviesRunnablePopPast mRetrieveMoviesRunnablePopPast;

    public void searchMoviesApi(String query, int pageNumber){
        if(mRetrieveMoviesRunnable != null){
            mRetrieveMoviesRunnable = null;
        }
        mRetrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);
        final Future myHandler = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveMoviesRunnable);
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() { myHandler.cancel(true); }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void searchMoviesPop(int pageNumber){
        if(mRetrieveMoviesRunnablePop != null){
            mRetrieveMoviesRunnablePop = null;
        }
        mRetrieveMoviesRunnablePop = new RetrieveMoviesRunnablePop(pageNumber);
        final Future myHandler2 = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveMoviesRunnablePop);
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    public void searchMoviesPopPast(int year, int pageNumber, String language){
        if(mRetrieveMoviesRunnablePopPast != null){
            mRetrieveMoviesRunnablePopPast=null;
        }
        mRetrieveMoviesRunnablePopPast = new RetrieveMoviesRunnablePopPast(year, pageNumber, language);
        final Future myHandler3 = AppExecutors.getInstance().getNetworkIO().submit(mRetrieveMoviesRunnablePopPast);
        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler3.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

}
