package com.framgia.soundcloudproject.screen.main.home;

import com.framgia.soundcloudproject.data.model.Genre;

import java.util.List;

public interface HomeContract {
    interface Presenter {
        void loadGenres();
    }

    interface View {
        void showGenres(List<Genre> genres);
    }
}
