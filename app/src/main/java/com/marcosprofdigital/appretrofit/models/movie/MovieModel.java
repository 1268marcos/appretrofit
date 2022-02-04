package com.marcosprofdigital.appretrofit.models.movie;
// 01.1
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieModel implements Parcelable{

    protected MovieModel (Parcel in){
        mTitle = in.readString();
        mPoster_path = in.readString();
        mRelease_date = in.readString();
        mMovie_id = in.readInt();
        mVote_average = in.readFloat();
        mMovie_overview = in.readString();
        mOriginal_title = in.readString();
        mRuntime = in.readInt();
        mOriginal_language = in.readString();
        mBackdrop_path = in.readString();
        mIsAdult = in.readByte() != 0;  //mIsAdult == true if byte != 0
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source); // in = source
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPoster_path() {
        return mPoster_path;
    }

    public void setPoster_path(String poster_path) {
        mPoster_path = poster_path;
    }

    public String getRelease_date() {
        return mRelease_date;
    }

    public void setRelease_date(String release_date) {
        mRelease_date = release_date;
    }

    public int getMovie_id() {
        return mMovie_id;
    }

    public void setMovie_id(int movie_id) {
        mMovie_id = movie_id;
    }

    public float getVote_average() {
        return mVote_average;
    }

    public void setVote_average(float vote_average) {
        mVote_average = vote_average;
    }

    public String getMovie_overview() {
        return mMovie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        mMovie_overview = movie_overview;
    }

    public String getOriginal_title() {
        return mOriginal_title;
    }

    public void setOriginal_title(String original_title) {
        mOriginal_title = original_title;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int runtime) {
        mRuntime = runtime;
    }

    public String getOriginal_language() {
        return mOriginal_language;
    }

    public void setOriginal_language(String original_language) {
        mOriginal_language = original_language;
    }

    public String getBackdrop_path() {
        return mBackdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        mBackdrop_path = backdrop_path;
    }

    public boolean isAdult() {
        return mIsAdult;
    }

    public MovieModel(String title, String poster_path, String release_date, int movie_id, float vote_average, String movie_overview, String original_title, int runtime, String original_language, String backdrop_path, boolean isAdult) {
        mTitle = title;
        mPoster_path = poster_path;
        mRelease_date = release_date;
        mMovie_id = movie_id;
        mVote_average = vote_average;
        mMovie_overview = movie_overview;
        mOriginal_title = original_title;
        mRuntime = runtime;
        mOriginal_language = original_language;
        mBackdrop_path = backdrop_path;
        mIsAdult = isAdult;
    }

    public void setAdult(boolean adult) {
        mIsAdult = adult;
    }

    @SerializedName("title")
    private String mTitle;

    @SerializedName("poster_path")
    private String mPoster_path;

    @SerializedName("release_date")
    private String mRelease_date;

    @SerializedName("movie_id")
    private int mMovie_id;

    @SerializedName("vote_average")
    private float mVote_average;

    @SerializedName("overview")
    private String mMovie_overview;

    @SerializedName("original_title")
    private String mOriginal_title;

    @SerializedName("runtime")
    private int mRuntime;

    @SerializedName("original_language")
    private String mOriginal_language;

    @SerializedName("backdrop_path")
    private String mBackdrop_path;

    @SerializedName("adult")
    private boolean mIsAdult;

    @Override
    public String toString() {
        return "MovieModel{" +
                "mTitle='" + mTitle + '\'' +
                ", mPoster_path='" + mPoster_path + '\'' +
                ", mRelease_date='" + mRelease_date + '\'' +
                ", mMovie_id=" + mMovie_id +
                ", mVote_average=" + mVote_average +
                ", mMovie_overview='" + mMovie_overview + '\'' +
                ", mOriginal_title='" + mOriginal_title + '\'' +
                ", mRuntime=" + mRuntime +
                ", mOriginal_language='" + mOriginal_language + '\'' +
                ", mBackdrop_path='" + mBackdrop_path + '\'' +
                ", mIsAdult=" + mIsAdult +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPoster_path);
        dest.writeString(mRelease_date);
        dest.writeInt(mMovie_id);
        dest.writeFloat(mVote_average);
        dest.writeString(mMovie_overview);
        dest.writeString(mOriginal_title);
        dest.writeInt(mRuntime);
        dest.writeString(mOriginal_language);
        dest.writeString(mBackdrop_path);
        dest.writeByte((byte) (mIsAdult ? 1: 0));  //if mIsAdult == true, byte == 1
    }
}
