package com.framgia.soundcloudproject.screen.main.library;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.favorite.FavoriteFragment;
import com.framgia.soundcloudproject.screen.main.playlist.PlaylistFragment;

import java.util.List;

public class LibraryFragment extends Fragment implements LibraryContract.View, View.OnClickListener {
    private static final int[] COMPONENT_ID = {
            R.id.image_favorites, R.id.image_playlists,
            R.id.image_songs, R.id.image_favorite_detail,
            R.id.image_playlist_detail, R.id.image_song_detail,
            R.id.text_favorites, R.id.text_playlists,
            R.id.text_songs
    };
    private LibraryContract.Presenter mPresenter;
    private TextView mTextViewFavoriteCount;
    private TextView mTextViewPlaylistCount;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance() {
        LibraryFragment libraryFragment = new LibraryFragment();
        return libraryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComponent(view);
        mPresenter = new LibraryPresenter(this);
        mPresenter.getFavoriteCount();
        mPresenter.getPlaylistCount();
    }

    @Override
    public void showFavoriteCount(int count) {
        String text = count + Constant.SPACE + getContext().getString(R.string.tracks);
        mTextViewFavoriteCount.setText(text);
    }

    @Override
    public void showPlaylistCount(int count) {
        String text = count + Constant.SPACE + getContext().getString(R.string.playlists);
        mTextViewPlaylistCount.setText(text);
    }

    @Override
    public void goToFavoriteFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.constraint_library, FavoriteFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void goToPlaylistFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.constraint_library, PlaylistFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showSongFragment() {
        // TODO
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_favorites:
            case R.id.image_favorite_detail:
            case R.id.text_favorites:
                goToFavoriteFragment();
                break;
            case R.id.image_playlists:
            case R.id.image_playlist_detail:
            case R.id.text_playlists:
                goToPlaylistFragment();
                break;
            case R.id.image_songs:
            case R.id.image_song_detail:
            case R.id.text_songs:
                // TODO
            default:
                break;
        }
    }

    private void setupComponent(View view) {
        mTextViewFavoriteCount = view.findViewById(R.id.text_num_favorite_tracks);
        mTextViewPlaylistCount = view.findViewById(R.id.text_num_playlists);
        mTextViewFavoriteCount.setOnClickListener(this);
        mTextViewPlaylistCount.setOnClickListener(this);
        for (int id : COMPONENT_ID) {
            view.findViewById(id).setOnClickListener(this);
        }
    }

    public void refresh() {
        mPresenter.getFavoriteCount();
    }
}
