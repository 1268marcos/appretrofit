package com.marcosprofdigital.appretrofit.models.tvseries;
// 01.2
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvSeriesModel implements Parcelable {

    //https://api.themoviedb.org/3/tv/popular?api_key=47e5804e0acb2aad0454f0abf2752b71&page=1
    //https://jsonformatter.curiousconcept.com
    //https://medium.com/techmacademy/how-to-implement-and-use-a-parcelable-class-in-android-part-1-28cca73fc2d1
    //https://medium.com/techmacademy/how-to-implement-and-use-a-parcelable-class-in-android-part-2-1793ce358bd0

    // Model Class for our TvSeries
    @SerializedName("backdrop_path")
    private String mBackdrop_path;

    @SerializedName("poster_path")
    private String mPoster_path;

    @SerializedName("first_air_date")
    private String mFirst_air_date;

    @SerializedName("id")
    private int mTvSeries_id;

    @SerializedName("name")
    private String mName;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("vote_average")
    private float mVote_average;

    @SerializedName("original_name")
    private String mOriginal_name;

    @SerializedName("original_language")
    private String mOriginal_language;

    @SerializedName("popularity")
    private float mPopularity;

    // Constructor
    public TvSeriesModel(String backdrop_path, String poster_path, String first_air_date, int tvSeries_id, String name, String overview, float vote_average, String original_name, String original_language, float popularity) {
        mBackdrop_path = backdrop_path;
        mPoster_path = poster_path;
        mFirst_air_date = first_air_date;
        mTvSeries_id = tvSeries_id;
        mName = name;
        mOverview = overview;
        mVote_average = vote_average;
        mOriginal_name = original_name;
        mOriginal_language = original_language;
        mPopularity = popularity;
    }


    protected TvSeriesModel(Parcel in) {
        mBackdrop_path = in.readString();
        mPoster_path = in.readString();
        mFirst_air_date = in.readString();
        mTvSeries_id = in.readInt();
        mName = in.readString();
        mOverview = in.readString();
        mVote_average = in.readFloat();
        mOriginal_name = in.readString();
        mOriginal_language = in.readString();
        mPopularity = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBackdrop_path);
        dest.writeString(mPoster_path);
        dest.writeString(mFirst_air_date);
        dest.writeInt(mTvSeries_id);
        dest.writeString(mName);
        dest.writeString(mOverview);
        dest.writeFloat(mVote_average);
        dest.writeString(mOriginal_name);
        dest.writeString(mOriginal_language);
        dest.writeFloat(mPopularity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvSeriesModel> CREATOR = new Creator<TvSeriesModel>() {
        @Override
        public TvSeriesModel createFromParcel(Parcel in) {
            return new TvSeriesModel(in);
        }

        @Override
        public TvSeriesModel[] newArray(int size) {
            return new TvSeriesModel[size];
        }
    };

    // Getters
    public String getBackdrop_path() {
        return mBackdrop_path;
    }

    public String getPoster_path() {
        return mPoster_path;
    }

    public String getFirst_air_date() {
        return mFirst_air_date;
    }

    public int getTvSeries_id() {
        return mTvSeries_id;
    }

    public String getName() {
        return mName;
    }

    public String getOverview() {
        return mOverview;
    }

    public float getVote_average() {
        return mVote_average;
    }

    public String getOriginal_name() {
        return mOriginal_name;
    }

    public String getOriginal_language() {
        return mOriginal_language;
    }

    public float getPopularity() {
        return mPopularity;
    }

    // To create use right button Generate & toString


    @Override
    public String toString() {
        return "TvSeriesModel{" +
                "mBackdrop_path='" + mBackdrop_path + '\'' +
                ", mPoster_path='" + mPoster_path + '\'' +
                ", mFirst_air_date='" + mFirst_air_date + '\'' +
                ", mTvSeries_id=" + mTvSeries_id +
                ", mName='" + mName + '\'' +
                ", mOverview='" + mOverview + '\'' +
                ", mVote_average=" + mVote_average +
                ", mOriginal_name='" + mOriginal_name + '\'' +
                ", mOriginal_language='" + mOriginal_language + '\'' +
                ", mPopularity=" + mPopularity +
                '}';
    }
}
