package com.framgia.soundcloudproject.screen.main.playlist;

import com.framgia.soundcloudproject.data.model.Playlist;

public class PlaylistPresenter implements PlaylistContract.Presenter {
    private PlaylistContract.View mView;

    public PlaylistPresenter(PlaylistContract.View view) {
        mView = view;
    }

    @Override
    public void getPlaylists() {

    }

    @Override
    public void getTracksInPlaylist(Playlist playlist) {

    }
}
