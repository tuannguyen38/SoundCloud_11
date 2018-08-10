package com.framgia.soundcloudproject.screen.main.playlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public class PlaylistFragment extends Fragment implements PlaylistContract.View {

    public PlaylistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }


    @Override
    public void showPlaylists(List<Playlist> playlists) {

    }

    @Override
    public void showTracks(List<Track> tracks) {

    }
}
