package com.framgia.soundcloudproject.screen.main.genredetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.remote.TrackDownloadManager;
import com.framgia.soundcloudproject.screen.main.TrackListener;
import com.framgia.soundcloudproject.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/13/2018.
 */
public class GenreDetailAdapter extends RecyclerView.Adapter<GenreDetailAdapter.GenreDetailViewHolder> {

    private TrackListener mTrackListener;
    private Context mContext;
    private List<Track> mTracks;
    private LayoutInflater mInflater;

    public GenreDetailAdapter(Context context, TrackListener trackListener) {
        mContext = context;
        mTrackListener = trackListener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GenreDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_track, parent, false);
        return new GenreDetailViewHolder(view, mTracks, mTrackListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreDetailViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    public void updateListTrack(List<Track> tracks) {
        if (tracks == null) return;
        if (mTracks == null) {
            mTracks = new ArrayList<>();
        }
        int startPosition = mTracks.size();
        mTracks.addAll(tracks);
        notifyItemRangeInserted(startPosition, tracks.size());
    }

    public void clearData() {
        if (mTracks != null) {
            mTracks.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * GenreDetailViewHolder
     */
    static class GenreDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImvTrack;
        private ImageView mImvOptions;
        private TextView mTvTitle;
        private TextView mTvArtist;
        private TextView mTvDuration;
        private TextView mTvDownloadCounts;
        private TextView mTvFavoriteCounts;
        private Track mCurrentTrack;
        private TrackListener mTrackListener;
        private List<Track> mTracks;
        private Context mContext;

        public GenreDetailViewHolder(View itemView, List<Track> tracks, TrackListener trackListener) {
            super(itemView);
            this.mTrackListener = trackListener;
            this.mTracks = tracks;
            this.mContext = itemView.getContext();
            mImvTrack = itemView.findViewById(R.id.image_track);
            mImvOptions = itemView.findViewById(R.id.image_options);
            mTvTitle = itemView.findViewById(R.id.text_title);
            mTvArtist = itemView.findViewById(R.id.text_artist);
            mTvDuration = itemView.findViewById(R.id.text_duration);
            mTvDownloadCounts = itemView.findViewById(R.id.text_number_download);
            mTvFavoriteCounts = itemView.findViewById(R.id.text_number_favorite);

            mImvOptions.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            if (mTracks == null) return;
            mCurrentTrack = mTracks.get(position);
            Glide.with(mContext).load(mCurrentTrack.getArtworkUrl())
                    .apply(new RequestOptions()
                            .override(Constant.DEFAULT_ITEM_SIZE)
                            .centerCrop()
                            .error(R.drawable.ic_holder))
                    .into(mImvTrack);
            mTvTitle.setText(mCurrentTrack.getTitle());
            mTvArtist.setText(mCurrentTrack.getPublisherAlbumTitle());
            mTvDownloadCounts.setText(String.valueOf(mCurrentTrack.getDownloadCount()));
            mTvFavoriteCounts.setText(String.valueOf(mCurrentTrack.getLikesCount()));
            mTvDuration.setText(StringUtil.convertMilisecondToTimer(mCurrentTrack.getFullDuration()));
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

        private void handleOptions() {
            PopupMenu popupMenu = new PopupMenu(mContext, mImvOptions);
            popupMenu.inflate(R.menu.menu_item_track);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_add_to_next_up:
                            mTrackListener.onAddToNextUp(mCurrentTrack);
                            return true;
                        case R.id.action_download:
                            mTrackListener.onDownloadTrack(mCurrentTrack);
                            return true;
                        case R.id.action_add_to_playlist:
                            mTrackListener.onAddToPlaylist(mCurrentTrack);
                            return true;
                        case R.id.action_add_farvotie:
                            mTrackListener.onAddToFavorite(mCurrentTrack);
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }

        private void handlePlayTracks() {
            if (mTrackListener == null) return;
            mTrackListener.onPlayTrack(getAdapterPosition(), mTracks);
        }
    }
}
