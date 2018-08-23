package com.framgia.soundcloudproject.screen.main;

import android.widget.Toast;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }

    @Override
    public void addToFavorite(Track track) {
        TrackRepository.getInstance().addTrackToFavorite(track, new TrackDataSource.OnQueryDatabaseListener() {
            @Override
            public void onQuerySuccess(String msg) {
                mView.showAddToFavoriteSuccess();
            }

            @Override
            public void onQueryFailure(Exception e) {
                mView.showAddToFavoriteError();
            }
        });
    }

    @Override
    public void removeFromFavorite(Track track) {
        TrackRepository.getInstance().deleteTrackFavorite(track, new TrackDataSource.OnQueryDatabaseListener() {
            @Override
            public void onQuerySuccess(String msg) {
                mView.showRemoveFromFavoriteSuccess();
            }

            @Override
            public void onQueryFailure(Exception e) {
                mView.showRemoveFromFavoriteError();
            }
        });
    }
}
