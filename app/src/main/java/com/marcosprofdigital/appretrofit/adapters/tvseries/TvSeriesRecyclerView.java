package com.marcosprofdigital.appretrofit.adapters.tvseries;

// 15.2
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marcosprofdigital.appretrofit.R;
import com.marcosprofdigital.appretrofit.models.tvseries.TvSeriesModel;
import com.marcosprofdigital.appretrofit.utils.Constants;

import java.util.List;

public class TvSeriesRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TvSeriesModel> mTvSeries;
    private OnTvSeriesListener mOnTvSeriesListener;

    // to create a best design interface
    private static final int DISPLAY_POP = 11;
    private static final int DISPLAY_SEARCH = 22;

    public TvSeriesRecyclerView(OnTvSeriesListener onTvSeriesListener){
        mOnTvSeriesListener = onTvSeriesListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView;
        int res_layout = R.layout.tvseries_popular_layout;
        if(viewType == DISPLAY_SEARCH){
            res_layout = R.layout.tvseries_list_item;
        }
        mView = LayoutInflater.from(parent.getContext()).inflate(res_layout, parent, false);
        return  new TvSeriesViewHolder(mView, mOnTvSeriesListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // ATTENTION PROGRAMMER - Here cause many errors - by Marcos
        int itemViewType = getItemViewType(position);
        if(itemViewType == DISPLAY_SEARCH){
            //ATTENTION MODEL
            ((TvSeriesViewHolder)holder).mTextViewTitleTvSeries.setText(mTvSeries.get(position).getName());
            ((TvSeriesViewHolder)holder).mTextViewReleaseDateTvSeries.setText(mTvSeries.get(position).getOriginal_name());
            ((TvSeriesViewHolder)holder).mTextViewLanguageTvSeries.setText(mTvSeries.get(position).getOriginal_language());
            ((TvSeriesViewHolder)holder).mTextViewDurationTvSeries.setText(mTvSeries.get(position).getFirst_air_date());

            ((TvSeriesViewHolder)holder).mRatingBarTvSeries.setRating((mTvSeries.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load(Constants.IMAGE_URL+mTvSeries.get(position).getBackdrop_path())
                    .into(((TvSeriesViewHolder)holder).mImageViewTvSeries);
        } else {
            ((TvSeriesPopularViewHolder)holder).mRatingBarTvSeriesPop.setRating((mTvSeries.get(position).getVote_average())/2);
            Glide.with(holder.itemView.getContext())
                    .load(Constants.IMAGE_URL+mTvSeries.get(position).getPoster_path())
                    .into(((TvSeriesPopularViewHolder)holder).mImageViewTvSeriesPop);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(Constants.POPULAR){
            return DISPLAY_POP;
        } else {
            return DISPLAY_SEARCH;
        }
    }

    @Override
    public int getItemCount() {
        if(mTvSeries != null){
            return  mTvSeries.size();
        }
        return 0;
    }

    public void setTvSeries(List<TvSeriesModel> tvSeries){
        mTvSeries = tvSeries;
        notifyDataSetChanged();
    }

    // Getting the id of the tvSeries clicked
    public TvSeriesModel getSelectedTvSeries(int position){
        if(mTvSeries != null){
            if(mTvSeries.size() > 0){
                return  mTvSeries.get(position);
            }
        }
        return null;
    }

}

