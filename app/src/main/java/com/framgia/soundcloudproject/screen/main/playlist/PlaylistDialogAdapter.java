package com.framgia.soundcloudproject.screen.main.playlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

import java.util.List;

public class PlaylistDialogAdapter extends RecyclerView.Adapter<PlaylistDialogAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<Playlist> mPlaylists;
    private Track mTrack;
    private TrackDataSource.OnQueryDatabaseListener mListener;

    public PlaylistDialogAdapter(Track track, TrackDataSource.OnQueryDatabaseListener listener) {
        mPlaylists = TrackRepository.getInstance().getPlaylist();
        mTrack = track;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mPlaylists == null) return;
        holder.bindData(mPlaylists.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaylists != null ? mPlaylists.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Playlist mPlaylist;
        private TextView mTextTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        public void bindData(Playlist playlist) {
            if (playlist == null) return;
            mPlaylist = playlist;
            mTextTitle.setText(playlist.getName());
        }

        @Override
        public void onClick(View v) {
            TrackRepository.getInstance()
                    .addTrackToPlaylist(mPlaylist.getId(), mListener, mTrack);
        }
    }
}

