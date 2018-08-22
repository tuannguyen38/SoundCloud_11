package com.framgia.soundcloudproject.screen.main.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.screen.main.TrackListener;
import com.framgia.soundcloudproject.utils.StringUtil;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private Context mContext;
    private List<Playlist> mPlaylists;
    private LayoutInflater mInflater;
    private TrackListener mTrackListener;

    public PlaylistAdapter(Context context, TrackListener trackListener) {
        mContext = context;
        mTrackListener = trackListener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view, mPlaylists, mTrackListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mPlaylists.get(position);
        holder.bindView(playlist);
    }


    @Override
    public int getItemCount() {
        return mPlaylists != null ? mPlaylists.size() : 0;
    }

    public void setPlaylists(List<Playlist> playlists) {
        mPlaylists = playlists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context mContext;
        private TextView mTextViewPlaylistName;
        private TextView mTextViewNumTracks;
        private ImageView mImageViewOption;
        private List<Playlist> mPlaylists;
        private TrackListener mTrackListener;

        public ViewHolder(View itemView, List<Playlist> playlists, TrackListener listener) {
            super(itemView);
            mContext = itemView.getContext();
            mPlaylists = playlists;
            mTrackListener = listener;
            mTextViewPlaylistName = itemView.findViewById(R.id.text_playlist_name);
            mTextViewNumTracks = itemView.findViewById(R.id.text_num_tracks);
            mImageViewOption = itemView.findViewById(R.id.image_playlist_option);
            mImageViewOption.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bindView(Playlist playlist) {
            mTextViewPlaylistName.setText(playlist.getName());
            String textNumTracks = StringUtil.formatStringNumberOfItems(playlist.getNumTracks(),
                    mContext.getString(R.string.tracks));
            mTextViewNumTracks.setText(textNumTracks);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_playlist_option:
                    handleOptions();
                    break;
                default:
                    handlePlayTrack();
            }
        }

        private void handleOptions() {

        }

        private void handlePlayTrack() {
            Playlist playlist = mPlaylists.get(getAdapterPosition());
            if (mTrackListener == null || playlist.getTracks() == null) return;
            mTrackListener.onPlayTrack(0, playlist.getTracks());
        }
    }
}
