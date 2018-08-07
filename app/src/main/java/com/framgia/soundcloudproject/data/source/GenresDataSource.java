package com.framgia.soundcloudproject.data.source;

import com.framgia.soundcloudproject.data.model.Genre;

import java.util.List;

public interface GenresDataSource {
    List<Genre> getGenres();
}
