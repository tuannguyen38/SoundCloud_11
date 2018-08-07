package com.framgia.soundcloudproject.screen.splash;

import com.framgia.soundcloudproject.BasePresenter;
import com.framgia.soundcloudproject.BaseView;

/**
 * Created by Hades on 8/7/2018.
 */
public class SplashContract {

    interface Presenter extends BasePresenter {
        void updateUI();
    }

    interface View extends BaseView<Presenter> {
        void gotoMainActivity();
    }
}
