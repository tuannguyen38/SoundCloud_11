package com.framgia.soundcloudproject.screen.main.favorite;

import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;

import java.util.List;

public class FavoritePresenter implements FavoriteContract.Presenter {
    private FavoriteContract.View mView;
    private TrackRepository mTrackRepository;

    public FavoritePresenter(FavoriteContract.View view) {
        mView = view;
        mTrackRepository = TrackRepository.getInstance();
    }

    @Override
    public void getAllFavorites() {
        List<Track> tracks = mTrackRepository.getTracksFavorite();
        mView.showFavorites(tracks);
    }
}
