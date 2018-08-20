package com.framgia.soundcloudproject.screen.main.search;

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
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.TrackListener;
import com.framgia.soundcloudproject.screen.main.genredetail.GenreDetailAdapter;
import com.framgia.soundcloudproject.utils.EndlessScrollListener;

import java.util.ArrayList;

/**
 * Created by Hades on 8/20/2018.
 */
public class SearchFragment extends Fragment implements SearchContract.View {

    private SearchContract.Presenter mPresenter;
    private GenreDetailAdapter mAdapter;
    private TrackListener mTrackListener;
    private RecyclerView mRecyclerSearch;
    private ProgressBar mProgressLoading;
    private String mQuery;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment genreDetailFragment = new SearchFragment();
        return genreDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new SearchPresenter(this);
        setupComponents(view);
    }

    private void setupComponents(View view) {
        mProgressLoading = view.findViewById(R.id.progress_loading);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new GenreDetailAdapter(getContext(), mTrackListener);
        mRecyclerSearch = view.findViewById(R.id.recycler_genre_detail);
        mRecyclerSearch.setLayoutManager(linearLayoutManager);
        mRecyclerSearch.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerSearch.setAdapter(mAdapter);
        mRecyclerSearch.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int offset) {
                mPresenter.searchTracks(mQuery, offset);
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
    public void showNoTrackAvailable() {
        Toast.makeText(getContext(), R.string.msg_no_track_available, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTracks(ArrayList<Track> trackList) {
        mAdapter.updateListTrack(trackList);
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
    public void showLoadingTrackError(String err) {
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    public void submitQueryText(String query) {
        mQuery = query;
        mPresenter.searchTracks(query, 0);
    }
}
