package com.framgia.soundcloudproject.screen.main.genredetail;

import com.framgia.soundcloudproject.BasePresenter;
import com.framgia.soundcloudproject.BaseView;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.ArrayList;

/**
 * Created by Hades on 8/13/2018.
 */
public interface GenreDetailContract {

    interface Presenter extends BasePresenter {
        void loadTracks(String genre, int limit, int offset);
    }

    interface View extends BaseView<Presenter> {
        void showLoadingIndicator();

        void hideLoadingIndicator();

        void showTrack(ArrayList<Track> tracks);

        void showNoTrackAvailable();

        void showLoadingTracksError(String error);
    }
}
