package com.framgia.soundcloudproject.screen.main.playtrack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

/**
 * Created by Hades on 8/19/2018.
 */
public class NextUpFragment extends BottomSheetDialogFragment {

    private static final String CURRENT_POSITION = "CURRENT_POSITION";
    private List<Track> mTracks;
    private NextUpAdapter.OnTrackItemClickListener mListener;
    private RecyclerView mRecyclerNextUp;
    private NextUpAdapter mNextUpAdapter;
    private int mCurrentPosition;

    public NextUpFragment(List<Track> tracks, int currentPosition) {
        mTracks = tracks;
        mCurrentPosition = currentPosition;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_next_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerNextUp = view.findViewById(R.id.recycler_next_up);
        mRecyclerNextUp.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerNextUp.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mNextUpAdapter = new NextUpAdapter(getContext(), mCurrentPosition, mListener);
        mNextUpAdapter.updateListTrack(mTracks);
        mRecyclerNextUp.setAdapter(mNextUpAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NextUpAdapter.OnTrackItemClickListener) {
            mListener = (NextUpAdapter.OnTrackItemClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
