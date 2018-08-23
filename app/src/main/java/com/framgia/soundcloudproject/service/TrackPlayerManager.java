package com.framgia.soundcloudproject.service;

import com.framgia.soundcloudproject.constant.LoopType;
import com.framgia.soundcloudproject.constant.ShuffleMode;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.service.TrackPlayerController.TrackInfoListener;

import java.util.List;

/**
 * Created by Hades on 8/13/2018.
 */
public interface TrackPlayerManager {

    int getCurrentState();

    void playNextTrack();

    void playPreviousTrack();

    void changeTrackState();

    void release();

    void seekTo(int percent);

    void setTrackInfoListener(TrackInfoListener listener);

    Track getCurrentTrack();

    int getCurrentTrackPosition();

    List<Track> getListTracks();

    void playTrackAtPosition(int position, Track... tracks);

    void addToNextUp(Track track);

    @LoopType
    int getLoopType();

    @ShuffleMode
    int getShuffleMode();

    void changeLoopType();

    void changeShuffleState();

    int getCurrentPosition();

    int getDuration();
}
