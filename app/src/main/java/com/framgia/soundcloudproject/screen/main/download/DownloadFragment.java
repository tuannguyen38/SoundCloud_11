package com.framgia.soundcloudproject.screen.main.download;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.TrackListener;

import java.util.List;

/**
 * Created by Hades on 8/9/2018.
 */
public class DownloadFragment extends Fragment implements DownloadContract.View {

    private static final int PERMISSION_REQUEST_STORAGE = 1;

    private DownloadContract.Presenter mPresenter;
    private TrackListener mTrackListener;
    private RecyclerView mRecyclerView;
    private OfflineTrackAdapter mAdapter;

    public DownloadFragment() {
    }

    public static DownloadFragment newInstance() {
        return new DownloadFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_download, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComponents(view);
        mPresenter = new DownloadPresenter(this);
        requestPermissionStorage();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.getOfflineTracks();
                } else {
                    Toast.makeText(getActivity(), R.string.error_permission_denied, Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    @Override
    public void showTracks(List<Track> tracks) {
        mAdapter.setTracks(tracks);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoTrackInDevice() {
        Toast.makeText(getContext(), R.string.msg_no_track_available, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    private void setupComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview_offline);
        mAdapter = new OfflineTrackAdapter(getContext(), mTrackListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void requestPermissionStorage() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        } else {
            mPresenter.getOfflineTracks();
        }
    }
}
