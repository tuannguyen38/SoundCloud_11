package com.framgia.soundcloudproject.screen.main.download;

import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

import java.util.List;

public class DownloadPresenter implements DownloadContract.Presenter,
        TrackDataSource.OnFetchDataListener<Track> {

    private DownloadContract.View mView;
    private TrackRepository mTrackRepository;

    public DownloadPresenter(DownloadContract.View view) {
        mView = view;
        mTrackRepository = TrackRepository.getInstance();
    }

    @Override
    public void getOfflineTracks() {
        mTrackRepository.getOfflineTracks(this);
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        if (data == null || data.isEmpty()) {
            mView.showNoTrackInDevice();
        } else {
            mView.showTracks(data);
        }
    }

    @Override
    public void onFetchDataFailure(Exception e) {
        mView.showError(e.getMessage());
    }
}
