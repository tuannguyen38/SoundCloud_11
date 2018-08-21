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

    @Override
    public void getOfflineTracksInFolder(String folderName,
                                         TrackDataSource.OnFetchDataListener<Track> listener) {
        if(mLocalDataSource != null) {
            mLocalDataSource.getOfflineTracksInFolder(folderName, listener);
        }
    }

    /**
     * Track Local
     */
    @Override
    public void getOfflineTracks(TrackDataSource.OnFetchDataListener<Track> listener) {
        if (mLocalDataSource != null) {
            mLocalDataSource.getOfflineTracks(listener);
        }
    }

    @Override
    public boolean deleteOfflineTrack(Track track) {
        return mLocalDataSource.deleteOfflineTrack(track);
    }

    @Override
    public List<Playlist> getPlaylist() {
        return mLocalDataSource == null ? null : mLocalDataSource.getPlaylist();
    }

    @Override
    public List<Track> getTrackInPlaylist(Playlist playlist) {
        return mLocalDataSource != null ? mLocalDataSource.getTrackInPlaylist(playlist) : null;
    }

    @Override
    public boolean isTrackInFavorite(Track track) {
        return mLocalDataSource.isTrackInFavorite(track);
    }

    @Override
    public boolean isTrackInPlaylist(Track track, Playlist playlist) {
        return mLocalDataSource.isTrackInPlaylist(track, playlist);
    }

    @Override
    public List<Track> getTracksFavorite() {
        return mLocalDataSource != null ? mLocalDataSource.getTracksFavorite() : null;
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
