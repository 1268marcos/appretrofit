package com.marcosprofdigital.appretrofit.adapters.movie;
// 15
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marcosprofdigital.appretrofit.R;
import com.marcosprofdigital.appretrofit.models.movie.MovieModel;
import com.marcosprofdigital.appretrofit.utils.Constants;

import java.util.List;
import java.util.Locale;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   private List<MovieModel> mMovies;

   private OnMovieListener mOnMovieListener;

   public static final int DISPLAY_POP = 1;
   public static final int DISPLAY_SEARCH = 2;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        mOnMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView;
//        if(viewType == DISPLAY_SEARCH){
//            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
//            return  new MovieViewHolder(mView, mOnMovieListener);
//
//        } else {
//            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_popular_layout, parent, false);
//            return  new MoviePopularViewHolder(mView, mOnMovieListener);
//        }
        int res_layout = R.layout.movies_popular_layout;
        if(viewType == DISPLAY_SEARCH){
            res_layout = R.layout.movie_list_item;
        }
        mView = LayoutInflater.from(parent.getContext()).inflate(res_layout, parent,false);
        return new MovieViewHolder(mView, mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if(itemViewType == DISPLAY_SEARCH){
            ((MovieViewHolder)holder).mTextViewTitle.setText(mMovies.get(position).getTitle());
            ((MovieViewHolder)holder).mTextViewReleaseDate.setText(mMovies.get(position).getRelease_date());

            ((MovieViewHolder)holder).mTextViewDuration.setText(""+mMovies.get(position).getRuntime());

            ((MovieViewHolder)holder).mTextViewLanguage.setText(mMovies.get(position).getOriginal_language().toUpperCase(Locale.ROOT));

            ((MovieViewHolder)holder).mRatingBar.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load(Constants.IMAGE_URL+mMovies.get(position).getBackdrop_path()) //.getPoster_path
                    .into(((MovieViewHolder)holder).mImageViewMovie);

        } else {
            ((MoviePopularViewHolder)holder).mRatingBarPop.setRating((mMovies.get(position).getVote_average())/2);
            Glide.with(holder.itemView.getContext())
                    .load(Constants.IMAGE_URL+mMovies.get(position).getPoster_path())
                    .into(((MoviePopularViewHolder)holder).mImageViewPop);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(Constants.POPULAR){
            return DISPLAY_POP;
        } else {
            return DISPLAY_SEARCH;
        }
    }

    @Override
    public int getItemCount() {
        if(mMovies != null){
            return mMovies.size();
        }
        return 0;
    }

    public void setMovies(List<MovieModel> movies){
        mMovies=movies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if(mMovies.size() > 0){
                return mMovies.get(position);
            }
        }
        return null;
    }


}
