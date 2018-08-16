package com.framgia.soundcloudproject.screen.main.genredetail;

import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/13/2018.
 */
public class GenreDetailPresenter implements GenreDetailContract.Presenter,
        TrackDataSource.OnFetchDataListener<Track> {

    private GenreDetailContract.View mView;
    private TrackRepository mTrackRepository;

    public GenreDetailPresenter(GenreDetailContract.View view, TrackRepository trackRepository) {
        mView = view;
        mTrackRepository = trackRepository;
    }

    @Override
    public void loadTracks(String genre, int limit, int offset) {
        mView.showLoadingIndicator();
        mTrackRepository.getTracksRemote(genre, limit, offset, this);
    }

    @Override
    public void onFetchDataSuccess(List<Track> data) {
        mView.hideLoadingIndicator();
        if (data == null || data.isEmpty()) {
            mView.showNoTrackAvailable();
            return;
        }
        mView.showTrack((ArrayList<Track>) data);
    }

    @Override
    public void onFetchDataFailure(Exception e) {
        mView.hideLoadingIndicator();
        mView.showLoadingTracksError(e.getMessage());
    }

    @Override
    public void start() {
    }
}
