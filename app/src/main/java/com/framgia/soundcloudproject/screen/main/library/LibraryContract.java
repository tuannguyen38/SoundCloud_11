package com.framgia.soundcloudproject.screen.main.library;

import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public interface LibraryContract {
    interface Presenter {
        void getFavoriteTracks();

        void getLocalSongs();
    }

    interface View {
        void showFavoriteTracks(List<Track> tracks);

        void showLocalSongs(List<Track> songs);
    }
}
