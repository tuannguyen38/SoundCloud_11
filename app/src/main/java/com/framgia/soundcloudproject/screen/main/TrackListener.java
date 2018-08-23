package com.framgia.soundcloudproject.screen.main;

import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

/**
 * Created by Hades on 8/9/2018.
 */
public interface TrackListener {

    void onPlayTrack(int position, List<Track> tracks);

    void onAddToNextUp(Track track);

    void onAddToPlaylist(Track track);

    void onAddToFavorite(Track track);

    void onDownloadTrack(Track track);

    void onRemoveTrackFromFavorite(Track track);
}

