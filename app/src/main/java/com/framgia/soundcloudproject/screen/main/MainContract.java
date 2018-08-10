package com.framgia.soundcloudproject.screen.main;

import com.framgia.soundcloudproject.BasePresenter;
import com.framgia.soundcloudproject.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showTabLayout();

        void hideTabLayout();
    }

    interface Presenter extends BasePresenter {
    }
}
