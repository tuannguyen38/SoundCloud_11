package com.framgia.soundcloudproject.screen.main.search;

import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/20/2018.
 */
public class SearchPresenter implements SearchContract.Presenter, TrackDataSource.OnFetchDataListener<Track> {

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void searchTracks(String name, int offset) {
        mView.showLoadingIndicator();
        TrackRepository.getInstance().searchTracksRemote(name, offset, this);
    }

    @Override
    public void start() {
        mView.setPresenter(this);
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        mView.hideLoadingIndicator();
        if (data == null || data.isEmpty()) {
            mView.showNoTrackAvailable();
            return;
        }
        mView.showTracks((ArrayList<Track>) data);
    }

    @Override
    public void onFetchDataFailure(Exception e) {
        mView.hideLoadingIndicator();
        mView.showLoadingTrackError(e.getMessage());
    }
}
