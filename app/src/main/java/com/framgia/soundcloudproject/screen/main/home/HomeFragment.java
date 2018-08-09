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

import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {
    private Context mContext;
    private HomeContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getContext();
        mRecyclerView = view.findViewById(R.id.recyclerview_genre);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mPresenter = new HomePresenter(this,
                GenreRepository.getInstance(new GenresLocalDataSource()));
        mPresenter.loadGenres();

        return view;
    }

    @Override
    public void showGenres(List<Genre> genres) {
        GenreAdapter adapter = new GenreAdapter(mContext, genres);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showSongsByGenre() {
        // TODO Show list song by genre
    }
}
