package com.framgia.soundcloudproject.screen.main.library;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;

import java.util.List;

public class LibraryPresenter implements LibraryContract.Presenter {
    private LibraryContract.View mView;
    private TrackRepository mTrackRepository;

    public LibraryPresenter(LibraryContract.View view) {
        mView = view;
        mTrackRepository = TrackRepository.getInstance();
    }

    @Override
    public void getFavoriteCount() {
        List<Track> tracks = mTrackRepository.getTracksFavorite();
        int count = (tracks != null) ? tracks.size() : 0;
        mView.showFavoriteCount(count);
    }

    @Override
    public void getPlaylistCount() {
        List<Playlist> playlists = mTrackRepository.getPlaylist();
        int count = (playlists != null) ? playlists.size() : 0;
        mView.showPlaylistCount(count);
    }
}
