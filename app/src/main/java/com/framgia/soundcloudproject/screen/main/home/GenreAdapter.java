package com.framgia.soundcloudproject.screen.main.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private Context mContext;
    private List<Genre> mGenres;
    private LayoutInflater mInflater;
    private OnGenreClickListener mListener;

    public GenreAdapter(Context context, List<Genre> genres) {
        mContext = context;
        mGenres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.item_genre, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Genre genre = mGenres.get(position);
        holder.bindView(genre, mListener);
    }

    @Override
    public int getItemCount() {
        return mGenres != null ? mGenres.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewGenre;
        ImageView mImageViewGenre;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewGenre = itemView.findViewById(R.id.text_genre);
            mImageViewGenre = itemView.findViewById(R.id.image_genre);
        }

        public void bindView(final Genre genre, final OnGenreClickListener listener) {
            mTextViewGenre.setText(genre.getGenreString());
            Glide.with(mImageViewGenre).load(genre.getGenreImage()).into(mImageViewGenre);
            mImageViewGenre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onGenreClicked(genre.getName());
                }
            });
        }
    }

    public void setGenres(List<Genre> genres) {
        this.mGenres = genres;
        notifyDataSetChanged();
    }

    public void setOnGenreClickListener(OnGenreClickListener listener) {
        mListener = listener;
    }

    interface OnGenreClickListener {
        void onGenreClicked(String genre);
    }
}
