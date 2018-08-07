package com.framgia.soundcloudproject.data.model;

public class Genre {
    private String mName;
    private int mGenreString;
    private int mGenreImage;

    public Genre(String name, int genreString, int genreImage) {
        mName = name;
        mGenreString = genreString;
        mGenreImage = genreImage;
    }

    public String getName() {
        return mName;
    }

    public int getGenreString() {
        return mGenreString;
    }

    public int getGenreImage() {
        return mGenreImage;
    }
}
