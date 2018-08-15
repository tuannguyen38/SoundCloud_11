package com.framgia.soundcloudproject.data.source;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

/**
 * Created by Hades on 8/7/2018.
 */

public interface TrackDataSource {
    /**
     * Track local
     */
    interface LocalDataSource {

        boolean deleteTrack(Track track);

        List<Track> getTracksFavorite();

        void addTrackToFavorite(Track track, OnQueryDatabaseListener listener);

        void deleteTrackFavorite(Track track, OnQueryDatabaseListener listener);

        void addTrackToPlaylist(int playlistId, OnQueryDatabaseListener listener, Track track);

        void removeTrackFromPlaylist(int playlistId, OnQueryDatabaseListener listener, Track track);

        void deletePlaylist(Playlist playlist, OnQueryDatabaseListener listener);

        void insertPlaylist(Playlist playlist, OnQueryDatabaseListener listener);

        void renamePlayList(Playlist playlist, OnQueryDatabaseListener listener);

        List<Playlist> getPlaylist();

        List<Track> getTrackInPlaylist(Playlist playlist);

    }

    /**
     * Tracks Remote
     */
    interface RemoteDataSource {
        void getTracksRemote(String genre, int limit, int offSet, OnFetchDataListener<Track> listener);

        void searchTracksRemote(String name, int offSet, OnFetchDataListener<Track> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(Exception e);
    }

    interface OnQueryDatabaseListener {
        void onQuerySuccess(String msg);

        void onQueryFailure(Exception e);
    }
}
