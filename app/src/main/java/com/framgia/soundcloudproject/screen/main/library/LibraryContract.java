package com.framgia.soundcloudproject.screen.main.library;

import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public interface LibraryContract {
    interface Presenter {
        void getFavoriteCount();

        void getPlaylistCount();
    }

    interface View {
        void showFavoriteCount(int count);

        void showPlaylistCount(int count);

        void goToFavoriteFragment();

        void goToPlaylistFragment();

        void showSongFragment();
    }
}
