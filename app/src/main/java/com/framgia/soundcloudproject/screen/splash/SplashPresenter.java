package com.framgia.soundcloudproject.screen.splash;

/**
 * Created by Hades on 8/7/2018.
 */
public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;

    public SplashPresenter(SplashContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }

    @Override
    public void updateUI() {
        mView.gotoMainActivity();
    }
}
