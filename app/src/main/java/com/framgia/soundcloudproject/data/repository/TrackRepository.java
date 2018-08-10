package com.framgia.soundcloudproject.data.repository;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;
import com.framgia.soundcloudproject.data.source.local.TrackLocalDataSource;
import com.framgia.soundcloudproject.data.source.remote.TrackRemoteDataSource;

import java.util.List;

/**
 * Created by Hades on 8/8/2018.
 */
public class TrackRepository implements TrackDataSource.LocalDataSource, TrackDataSource.RemoteDataSource {

    private static TrackRepository sTrackRepository;
    private TrackDataSource.LocalDataSource mLocalDataSource;
    private TrackDataSource.RemoteDataSource mRemoteDataSource;

    private TrackRepository(TrackDataSource.LocalDataSource localDataSource,
                            TrackDataSource.RemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static TrackRepository getInstance() {
        if (sTrackRepository == null) {
            sTrackRepository = new TrackRepository(TrackLocalDataSource.getInstance(),
                    TrackRemoteDataSource.getInstance());
        }
        return sTrackRepository;
    }

    /**
     * Track Remote
     */
    @Override
    public void getTracksLocal(TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.getTracksLocal(listener);
        }
    }

    @Override
    public List<Playlist> getPlaylist() {
        return mLocalDataSource == null ? null : mLocalDataSource.getPlaylist();
    }

    @Override
    public void getTracksFavorite(TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.getTracksFavorite(listener);
        }
    }

    @Override
    public void searchTracksLocal(String trackName, TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.searchTracksLocal(trackName, listener);
        }
    }

    @Override
    public boolean deleteTrack(Track track) {
        return mLocalDataSource != null && mLocalDataSource.deleteTrack(track);
    }

    @Override
    public void addTrackToPlaylist(int playlistId, TrackDataSource.OnQueryDatabaseListener listener, Track track) {
        if (mLocalDataSource != null) {
            mLocalDataSource.addTrackToPlaylist(playlistId, listener, track);
        }
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, TrackDataSource.OnQueryDatabaseListener listener, Track track) {
        if (mLocalDataSource != null) {
            mLocalDataSource.removeTrackFromPlaylist(playlistId, listener, track);
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.deletePlaylist(playlist, listener);
        }
    }

    @Override
    public void insertPlaylist(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.insertPlaylist(playlist, listener);
        }
    }

    @Override
    public void addTrackToFavorite(Track track, TrackDataSource.OnQueryDatabaseListener listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.addTrackToFavorite(track, listener);
        }
    }

    @Override
    public void deleteTrackFavorite(Track track, TrackDataSource.OnQueryDatabaseListener listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.deleteTrackFavorite(track, listener);
        }
    }

    @Override
    public void renamePlayList(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.renamePlayList(playlist, listener);
        }
    }

    /**
     * Track Remote
     */
    @Override
    public void getTracksRemote(String genre, int limit, int offSet, TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mRemoteDataSource != null) {
            mRemoteDataSource.getTracksRemote(genre, limit, offSet, listener);
        }
    }

    @Override
    public void searchTracksRemote(String name, int offSet, TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mRemoteDataSource != null) {
            mRemoteDataSource.searchTracksRemote(name, offSet, listener);
        }
    }
}
