package com.framgia.soundcloudproject.screen.main.playtrack;

import com.framgia.soundcloudproject.BasePresenter;
import com.framgia.soundcloudproject.BaseView;
import com.framgia.soundcloudproject.constant.State;
import com.framgia.soundcloudproject.data.model.Track;

/**
 * Created by Hades on 8/15/2018.
 */
public class PlayTrackContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        void hideLoading();
    }
}
