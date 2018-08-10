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

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private Context mContext;
    private List<Playlist> mPlaylists;
    private LayoutInflater mInflater;

    public PlaylistAdapter(Context context, List<Playlist> playlists) {
        mContext = context;
        mPlaylists = playlists;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Playlist playlist = mPlaylists.get(position);
        holder.bindView(playlist);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewPlaylistName;
        private TextView mTextViewNumTracks;
        private ImageView mImageViewOption;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewPlaylistName = itemView.findViewById(R.id.text_playlist_name);
            mTextViewNumTracks = itemView.findViewById(R.id.text_num_tracks);
            mImageViewOption = itemView.findViewById(R.id.image_playlist_option);
        }

        public void bindView(Playlist playlist) {
            mTextViewPlaylistName.setText(playlist.getName());
            mTextViewNumTracks.setText(playlist.getNumTracks());
        }
    }
}
