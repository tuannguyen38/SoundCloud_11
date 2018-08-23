package com.framgia.soundcloudproject.screen.main;

import com.framgia.soundcloudproject.BasePresenter;
import com.framgia.soundcloudproject.BaseView;
import com.framgia.soundcloudproject.constant.State;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showTabLayout();

        void hideTabLayout();

        void showLoading();

        void hideLoading();

        void updateStateInfo(@State int state);

        void updateTrackInfo(Track track);

        void showAddToFavoriteSuccess();

        void showAddToFavoriteError();

        void showRemoveFromFavoriteSuccess();

        void showRemoveFromFavoriteError();
    }

    interface Presenter extends BasePresenter {

        void addToFavorite(Track track);

        void removeFromFavorite(Track track);
    }
}
