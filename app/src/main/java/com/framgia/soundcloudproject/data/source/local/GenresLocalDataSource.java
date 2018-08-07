package com.framgia.soundcloudproject.data.source.local;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.GenreEntity;
import com.framgia.soundcloudproject.data.model.Genre;
import com.framgia.soundcloudproject.data.source.GenresDataSource;

import java.util.ArrayList;
import java.util.List;

public class GenresLocalDataSource implements GenresDataSource {

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(GenreEntity.ALL_MUSIC, R.string.genre_all_music, R.raw.all_music));
        genres.add(new Genre(GenreEntity.ALL_AUDIO, R.string.genre_all_audio, R.raw.all_audio));
        genres.add(new Genre(GenreEntity.ALTERNATIVE, R.string.genre_alternative_rock, R.raw.alternative_rock));
        genres.add(new Genre(GenreEntity.AMBIENT, R.string.genre_ambient, R.raw.ambient));
        genres.add(new Genre(GenreEntity.CLASSICAL, R.string.genre_classical, R.raw.classical));
        genres.add(new Genre(GenreEntity.COUNTRY, R.string.genre_country, R.raw.country));
        return genres;
    }
}
