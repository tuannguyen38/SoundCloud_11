package com.framgia.soundcloudproject.data.repository;

import com.framgia.soundcloudproject.data.model.Genre;
import com.framgia.soundcloudproject.data.source.GenresDataSource;

import java.util.List;

public class GenreRepository {
    private static GenreRepository sInstance;
    private GenresDataSource mGenresLocalDataSource;

    private GenreRepository(GenresDataSource genresLocalDataSource) {
        this.mGenresLocalDataSource = genresLocalDataSource;
    }

    public static GenreRepository getInstance(GenresDataSource genresLocalDataSource) {
        if (sInstance == null) {
            sInstance = new GenreRepository(genresLocalDataSource);
        }
        return sInstance;
    }

    public List<Genre> getGenres() {
        return mGenresLocalDataSource.getGenres();
    }
}
