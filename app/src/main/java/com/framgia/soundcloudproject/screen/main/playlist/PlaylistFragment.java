package com.framgia.soundcloudproject.screen.main.playlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.TrackListener;

import java.util.List;

public class PlaylistFragment extends Fragment implements PlaylistContract.View {

    private PlaylistContract.Presenter mPresenter;
    private TrackListener mTrackListener;
    private PlaylistAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
        mPresenter = new PlaylistPresenter(this);
        mPresenter.getPlaylists();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TrackListener) {
            mTrackListener = (TrackListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mTrackListener = null;
    }

    @Override
    public void showPlaylists(List<Playlist> playlists) {
        mAdapter.setPlaylists(playlists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTracks(List<Track> tracks) {

    }

    private void setupView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_playlist);
        mAdapter = new PlaylistAdapter(getContext(), mTrackListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
