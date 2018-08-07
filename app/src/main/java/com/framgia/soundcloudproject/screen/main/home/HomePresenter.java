package com.framgia.soundcloudproject.screen.main.home;

import com.framgia.soundcloudproject.data.model.Genre;
import com.framgia.soundcloudproject.data.repository.GenreRepository;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private GenreRepository mGenreRepository;

    public HomePresenter(HomeContract.View view, GenreRepository genreRepository) {
        mView = view;
        mGenreRepository = genreRepository;
    }

    @Override
    public void loadGenres() {
        List<Genre> genres = mGenreRepository.getGenres();
        mView.showGenres(genres);
    }

    @Override
    public void loadSongsByGenre() {
        // TODO Get list song by genre
    }
}
