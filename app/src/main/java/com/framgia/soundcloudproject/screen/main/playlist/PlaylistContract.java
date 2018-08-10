package com.framgia.soundcloudproject.screen.main.playlist;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public interface PlaylistContract {
    interface Presenter {
        void getPlaylists();

        void getTracksInPlaylist(Playlist playlist);
    }

    interface View {
        void showPlaylists(List<Playlist> playlists);

        void showTracks(List<Track> tracks);
    }
}
