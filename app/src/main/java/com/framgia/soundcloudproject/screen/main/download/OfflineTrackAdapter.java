package com.framgia.soundcloudproject.screen.main.download;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.TrackListener;
import com.framgia.soundcloudproject.utils.StringUtil;

import java.util.List;

public class OfflineTrackAdapter extends RecyclerView.Adapter<OfflineTrackAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Track> mTracks;
    private TrackListener mTrackListener;

    public OfflineTrackAdapter(Context context, TrackListener trackListener) {
        mContext = context;
        mTrackListener = trackListener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_offline_track, parent, false);
        return new ViewHolder(view, mTracks, mTrackListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mTracks != null ? mTracks.size() : 0;
    }

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private TrackListener mTrackListener;
        private List<Track> mTracks;
        private TextView mTextViewTitle;
        private TextView mTextViewArtist;
        private TextView mTextViewDuration;
        private ImageView mImageViewOptions;

        public ViewHolder(View itemView, List<Track> tracks, TrackListener trackListener) {
            super(itemView);
            this.mContext = itemView.getContext();
            this.mTracks = tracks;
            this.mTrackListener = trackListener;
            this.mTextViewTitle = itemView.findViewById(R.id.text_offline_title);
            this.mTextViewArtist = itemView.findViewById(R.id.text_offline_artist);
            this.mTextViewDuration = itemView.findViewById(R.id.text_offline_duration);
            this.mImageViewOptions = itemView.findViewById(R.id.image_offline_options);
            this.mImageViewOptions.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            Track track = mTracks.get(position);
            mTextViewTitle.setText(track.getTitle());
            mTextViewArtist.setText(track.getPublisherAlbumTitle());
            mTextViewDuration.setText(StringUtil.convertMilisecondToTimer(track.getFullDuration()));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_offline_options:
                    handleOption();
                    break;
                default:
                    handlePlayTrack();
            }
        }

        private void handleOption() {
            PopupMenu popupMenu = new PopupMenu(mContext, mImageViewOptions);
            popupMenu.inflate(R.menu.menu_item_offline_track);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_play_offline:
                            handlePlayTrack();
                            return true;
                        case R.id.action_delete_track:
                            // TODO delete track
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }

        private void handlePlayTrack() {
            if (mTrackListener == null) return;
            mTrackListener.onPlayTrack(getAdapterPosition(), mTracks);
        }
    }
}
