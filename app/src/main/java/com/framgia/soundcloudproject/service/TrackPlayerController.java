package com.framgia.soundcloudproject.service;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.constant.LoopType;
import com.framgia.soundcloudproject.constant.ShuffleMode;
import com.framgia.soundcloudproject.constant.State;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.utils.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Hades on 8/13/2018.
 */
public class TrackPlayerController implements TrackPlayerManager {

    private static final long DELAY_MILLIS = 500;
    @State
    private int mState;
    @LoopType
    private int mLoopType = LoopType.NO_LOOP;
    @ShuffleMode
    private int mShuffleMode;

    private MediaPlayer mMediaPlayer;
    private TrackService mTrackService;
    private TrackInfoListener mInfoListener;
    private int mCurrentTrackPosition;
    private List<Track> mTracks;
    private List<Track> mOriginalTracks;

    public TrackPlayerController(TrackService trackService) {
        mTrackService = trackService;
    }

    private final MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return true;
        }
    };

    private final MediaPlayer.OnCompletionListener mCompletion = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            notifyStateChanged(State.PAUSE);
            handlePlayTrackWithLoopType();
        }
    };

    private final MediaPlayer.OnPreparedListener mOnPrepared = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mMediaPlayer.start();
            notifyStateChanged(State.PLAYING);
        }
    };

    @Override
    public int getCurrentState() {
        return mState;
    }

    @Override
    public void playNextTrack() {
        if (mCurrentTrackPosition < mTracks.size() - 1) {
            mCurrentTrackPosition++;
            prepareTrack();
        }
    }

    @Override
    public void playPreviousTrack() {
        if (mCurrentTrackPosition > 0) {
            mCurrentTrackPosition--;
            prepareTrack();
        }
    }

    @Override
    public void changeTrackState() {
        if (mMediaPlayer == null) return;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            notifyStateChanged(State.PAUSE);
        } else {
            mMediaPlayer.start();
            notifyStateChanged(State.PLAYING);
            // TODO
        }
    }

    @Override
    public void release() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    @Override
    public void seekTo(int percent) {
        if (mState == State.PAUSE || mState == State.PLAYING) {
            mMediaPlayer.seekTo(mMediaPlayer.getDuration() / 100 * percent);
        }
    }

    @Override
    public void setTrackInfoListener(TrackInfoListener listener) {
        mInfoListener = listener;
    }

    @Override
    public Track getCurrentTrack() {
        return mTracks == null ? null : mTracks.get(mCurrentTrackPosition);
    }

    @Override
    public int getCurrentTrackPosition() {
        return mCurrentTrackPosition;
    }

    @Override
    public List<Track> getListTracks() {
        return mTracks;
    }

    @Override
    public void playTrackAtPosition(int position, Track... tracks) {
        if (tracks == null && mTracks == null) {
            notifyStateChanged(State.INVALID);
            return;
        }
        if (mCurrentTrackPosition == position) return;
        if (tracks != null && tracks.length != 0) {
            mTracks = new ArrayList<>();
            Collections.addAll(mTracks, tracks);
        }
        mCurrentTrackPosition = position;
        prepareTrack();
    }

    @Override
    public void addToNextUp(Track track) {
        if (mTracks == null || mTracks.isEmpty()) return;
        mTracks.add(track);
        mOriginalTracks.add(track);
    }

    @Override
    public int getLoopType() {
        return mLoopType;
    }

    @Override
    public int getShuffleMode() {
        return mShuffleMode;
    }

    @Override
    public void changeLoopType() {
        switch (mLoopType) {
            case LoopType.NO_LOOP:
                mLoopType = LoopType.LOOP_LIST;
                break;
            case LoopType.LOOP_LIST:
                mLoopType = LoopType.LOOP_ONE;
                break;
            case LoopType.LOOP_ONE:
                mLoopType = LoopType.NO_LOOP;
                break;
        }
        if (mInfoListener == null) return;
        mInfoListener.onLoopChanged(mLoopType);
    }

    @Override
    public void changeShuffleState() {
        Track currentTrack = null;
        switch (mShuffleMode) {
            case ShuffleMode.ON:
                mShuffleMode = ShuffleMode.OFF;
                currentTrack = mTracks.get(mCurrentTrackPosition);
                mTracks = new ArrayList<>();
                mTracks.addAll(mOriginalTracks);
                break;
            case ShuffleMode.OFF:
                mShuffleMode = ShuffleMode.ON;
                mOriginalTracks = new ArrayList<>();
                mOriginalTracks.addAll(mTracks);
                currentTrack = mTracks.get(mCurrentTrackPosition);
                Collections.shuffle(mTracks);
                break;
        }
        mCurrentTrackPosition = mTracks.indexOf(currentTrack);
        if (mInfoListener == null) return;
        mInfoListener.onShuffleChanged(mShuffleMode);
    }

    private void notifyStateChanged(@State int state) {
        mState = state;
        if (mTrackService != null) {
            if (state == State.PREPARE) mTrackService.loadImage();
            mTrackService.createNotification(state);
        }
        if (mInfoListener == null) return;
        mInfoListener.onStateChanged(mState);
    }

    private void prepareTrack() {
        if (mTracks == null || mTracks.isEmpty()) {
            notifyStateChanged(State.INVALID);
            return;
        }
        release();
        notifyStateChanged(State.PREPARE);
        loadTrack();
        if (mInfoListener == null) return;
        mInfoListener.onTrackChanged(mTracks.get(mCurrentTrackPosition));
    }

    private void loadTrack() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            String path = mTracks.get(mCurrentTrackPosition).getUri();
            String url;
            if (path.contains(Constant.STORAGE)) {
                url = path;
            } else {
                url = StringUtil.formatTrackStreamURL(mTracks.get(mCurrentTrackPosition).getUri());
            }
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(mCompletion);
            mMediaPlayer.setOnPreparedListener(mOnPrepared);
            mMediaPlayer.setOnErrorListener(mErrorListener);
        } catch (IOException e) {
            Logger.getLogger(e.toString());
        }
    }

    private void handlePlayTrackWithLoopType() {
        switch (mLoopType) {
            case LoopType.NO_LOOP:
                mMediaPlayer.setLooping(false);
                playNextTrack();
                break;
            case LoopType.LOOP_ONE:
                mMediaPlayer.setLooping(true);
                prepareTrack();
                break;
            case LoopType.LOOP_LIST:
                mMediaPlayer.setLooping(false);
                if (mCurrentTrackPosition == mTracks.size() - 1) {
                    mCurrentTrackPosition = -1;
                }
                playNextTrack();
                break;
        }
    }

    public interface TrackInfoListener {

        void onTrackChanged(Track track);

        void onStateChanged(@State int state);

        void onLoopChanged(@LoopType int loopType);

        void onShuffleChanged(@ShuffleMode int shuffleMode);
    }
}
