package com.framgia.soundcloudproject.screen.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Genre;
import com.framgia.soundcloudproject.data.repository.GenreRepository;
import com.framgia.soundcloudproject.data.source.local.GenresLocalDataSource;
import com.framgia.soundcloudproject.screen.main.genredetail.GenreDetailFragment;

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View, GenreAdapter.OnGenreClickListener {
    private Context mContext;
    private HomeContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private GenreAdapter mGenreAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        // TODO
        return homeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getContext();
        mRecyclerView = view.findViewById(R.id.recyclerview_genre);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mGenreAdapter = new GenreAdapter(mContext, null);
        mRecyclerView.setAdapter(mGenreAdapter);
        mGenreAdapter.setOnGenreClickListener(this);
        mPresenter = new HomePresenter(this,
                GenreRepository.getInstance(new GenresLocalDataSource()));
        mPresenter.loadGenres();
        return view;
    }

    @Override
    public void showGenres(List<Genre> genres) {
        mGenreAdapter.setGenres(genres);
    }

    private void gotoDetailFragment(String genre) {
        GenreDetailFragment detailFragment = GenreDetailFragment.newInstance(genre);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_genre_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onGenreClicked(String genre) {
        gotoDetailFragment(genre);
    }
}
