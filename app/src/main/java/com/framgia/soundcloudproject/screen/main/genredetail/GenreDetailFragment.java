package com.framgia.soundcloudproject.screen.main.genredetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.screen.main.TrackListener;
import com.framgia.soundcloudproject.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/13/2018.
 */
public class GenreDetailFragment extends Fragment implements GenreDetailContract.View {

    private GenreDetailContract.Presenter mPresenter;
    private GenreDetailAdapter mAdapter;
    private TrackListener mTrackListener;
    private List<Track> mTracks;
    private RecyclerView mRecyclerTracks;
    private ProgressBar mProgressLoading;
    private String mGenre;

    public GenreDetailFragment() {
    }

    public static GenreDetailFragment newInstance(String genre) {
        GenreDetailFragment genreDetailFragment = new GenreDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.GENRE_TYPE, genre);
        genreDetailFragment.setArguments(bundle);
        return genreDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_genre_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new GenreDetailPresenter(this, TrackRepository.getInstance());
        mGenre = getArguments().getString(Constant.GENRE_TYPE);
        setupComponents(view);
        mPresenter.loadTracks(mGenre, Constant.LIMIT_DEFAULT, Constant.OFFSET_DEFAULT);
    }

    private void setupComponents(View view) {
        mProgressLoading = view.findViewById(R.id.progress_loading);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new GenreDetailAdapter(getContext(), mTrackListener);
        mRecyclerTracks = view.findViewById(R.id.recycler_genre_detail);
        mRecyclerTracks.setLayoutManager(linearLayoutManager);
        mRecyclerTracks.setHasFixedSize(true);
        mRecyclerTracks.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerTracks.setAdapter(mAdapter);
        mRecyclerTracks.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int offset) {
                mPresenter.loadTracks(mGenre, Constant.LIMIT_DEFAULT, offset);
            }
        });
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
    public void showLoadingIndicator() {
        mProgressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressLoading.setVisibility(View.GONE);
    }

    @Override
    public void showTrack(ArrayList<Track> tracks) {
        mAdapter.updateListTrack(tracks);
    }

    @Override
    public void showNoTrackAvailable() {
        Toast.makeText(getContext(), R.string.msg_no_track_available, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingTracksError(String error) {
    }

    @Override
    public void setPresenter(GenreDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
