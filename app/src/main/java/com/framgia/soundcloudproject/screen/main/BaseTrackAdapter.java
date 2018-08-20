package com.framgia.soundcloudproject.screen.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.utils.StringUtil;

import java.util.List;

public class BaseTrackAdapter<V extends BaseTrackAdapter.BaseViewHolder>
        extends RecyclerView.Adapter<V> {

    protected Context mContext;
    protected TrackListener mTrackListener;
    protected List<Track> mTracks;
    protected LayoutInflater mInflater;

    public BaseTrackAdapter(Context context, TrackListener trackListener) {
        mContext = context;
        mTrackListener = trackListener;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        protected Context mContext;
        protected TrackListener mTrackListener;
        protected List<Track> mTracks;
        protected ImageView mImageViewTrack;
        protected ImageView mImageViewOptions;
        protected ImageView mImageViewFavorite;
        protected TextView mTextViewTitle;
        protected TextView mTextViewArtist;
        protected TextView mTextViewDuration;
        protected TextView mTextViewDownloadCounts;

        public BaseViewHolder(View itemView, List<Track> tracks, TrackListener trackListener) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mTrackListener = trackListener;
            this.mTracks = tracks;
            mImageViewFavorite = itemView.findViewById(R.id.image_favorite);
            mImageViewOptions = itemView.findViewById(R.id.image_options);
            mImageViewTrack = itemView.findViewById(R.id.image_track);
            mTextViewArtist = itemView.findViewById(R.id.text_artist);
            mTextViewDownloadCounts = itemView.findViewById(R.id.text_number_download);
            mTextViewDuration = itemView.findViewById(R.id.text_duration);
            mTextViewTitle = itemView.findViewById(R.id.text_title);

            setupUI(itemView);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_options:
                    handleOptions();
                    break;
                default:
                    handlePlayTracks();
                    break;
            }
        }

        protected void bindData(int position) {
            if (mTracks == null) return;
            Track track = mTracks.get(position);
            Glide.with(mContext).load(track.getArtworkUrl()).into(mImageViewTrack);
            mTextViewTitle.setText(track.getTitle());
            mTextViewArtist.setText(track.getPublisherAlbumTitle());
            mTextViewDownloadCounts.setText(String.valueOf(track.getDownloadCount()));
            mTextViewDuration.setText(StringUtil.convertMilisecondToTimer(track.getFullDuration()));
        }

        protected void handlePlayTracks() {
            if (mTrackListener == null) return;
            mTrackListener.onPlayTrack(getAdapterPosition(), mTracks);
        }

        protected abstract void setupUI(View view);

        protected abstract void handleOptions();
    }
}
