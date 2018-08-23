package com.framgia.soundcloudproject.screen.main.playtrack;

/**
 * Created by Hades on 8/15/2018.
 */
public class PlayTrackPresenter implements PlayTrackContract.Presenter {

    private PlayTrackContract.View mView;

    public PlayTrackPresenter(PlayTrackContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }
}
