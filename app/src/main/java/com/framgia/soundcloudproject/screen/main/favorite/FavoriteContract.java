package com.framgia.soundcloudproject.screen.main.favorite;

import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public interface FavoriteContract {
    interface Presenter {
        void getAllFavorites();
    }

    interface View {
        void showFavorites(List<Track> tracks);
    }
}
