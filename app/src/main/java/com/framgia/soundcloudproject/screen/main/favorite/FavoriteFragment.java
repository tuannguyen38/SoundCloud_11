package com.framgia.soundcloudproject.screen.main.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.TrackListener;
import com.framgia.soundcloudproject.screen.main.download.OfflineTrackAdapter;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteContract.View{

    private FavoriteContract.Presenter mPresenter;
    private TrackListener mTrackListener;
    private RecyclerView mRecyclerView;
    private OfflineTrackAdapter mAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComponents(view);
        mPresenter = new FavoritePresenter(this);
        mPresenter.getAllFavorites();
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
    public void showFavorites(List<Track> tracks) {
        mAdapter.setTracks(tracks);
        mAdapter.notifyDataSetChanged();
    }

    private void setupComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_favorite);
        mAdapter = new OfflineTrackAdapter(getContext(), mTrackListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
