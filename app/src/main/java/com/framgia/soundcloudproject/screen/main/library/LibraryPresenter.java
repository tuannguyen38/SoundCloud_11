package com.framgia.soundcloudproject.screen.main.library;

import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public class LibraryPresenter implements LibraryContract.Presenter {
    private LibraryContract.View mView;
    // TODO Add data repository

    public LibraryPresenter(LibraryContract.View view) {
        mView = view;
    }

    // TODO Implement methods
    @Override
    public void getFavoriteTracks() {

    }

    @Override
    public void getLocalSongs() {

    }
}
