package com.framgia.soundcloudproject.screen.main.library;

import android.os.Bundle;
<<<<<<<HEAD
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
=======
        >>>>>>>Init tab:Library
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public class LibraryFragment extends Fragment implements LibraryContract.View {
    private LibraryContract.Presenter mPresenter;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }


    @Override
    public void showFavoriteTracks(List<Track> tracks) {

    }

    @Override
    public void showLocalSongs(List<Track> songs) {

    }
}
