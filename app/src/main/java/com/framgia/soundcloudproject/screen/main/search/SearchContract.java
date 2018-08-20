package com.framgia.soundcloudproject.screen.main.search;

import com.framgia.soundcloudproject.BasePresenter;
import com.framgia.soundcloudproject.BaseView;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.ArrayList;

/**
 * Created by Hades on 8/20/2018.
 */
public class SearchContract {

    interface Presenter extends BasePresenter {

        void searchTracks(String name, int offset);
    }

    interface View extends BaseView<Presenter> {

        void showNoTrackAvailable();

        void showTracks(ArrayList<Track> trackList);

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showLoadingTrackError(String err);
    }
}
