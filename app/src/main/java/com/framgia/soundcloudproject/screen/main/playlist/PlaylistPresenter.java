package com.framgia.soundcloudproject.screen.main.playlist;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;

import java.util.List;

public class PlaylistPresenter implements PlaylistContract.Presenter {
    private PlaylistContract.View mView;
    private TrackRepository mTrackRepository;

    public PlaylistPresenter(PlaylistContract.View view) {
        mView = view;
        mTrackRepository = TrackRepository.getInstance();
    }

    @Override
    public void getPlaylists() {
        List<Playlist> playlists = mTrackRepository.getPlaylist();
        for(Playlist playlist: playlists) {
            playlist.setTracks(mTrackRepository.getTrackInPlaylist(playlist));
        }
        mView.showPlaylists(playlists);
    }

    @Override
    public void getTracksInPlaylist(Playlist playlist) {
        List<Track> tracks = playlist.getTracks();
        mView.showTracks(tracks);
    }
}
