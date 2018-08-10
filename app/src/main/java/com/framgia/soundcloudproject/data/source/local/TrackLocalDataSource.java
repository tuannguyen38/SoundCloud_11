package com.framgia.soundcloudproject.data.source.local;

import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

import java.util.List;

/**
 * Created by Hades on 8/8/2018.
 */
public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sInstance;

    private TrackLocalDataSource() {
    }

    public static TrackLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource();
        }
        return sInstance;
    }

    @Override
    public void getTracksLocal(TrackDataSource.OnFetchDataListener<Track> listener) {
    }

    @Override
    public List<Playlist> getPlaylist() {
        return null;
    }

    @Override
    public void getTracksFavorite(TrackDataSource.OnFetchDataListener<Track> listener) {
    }

    @Override
    public void searchTracksLocal(String trackName, TrackDataSource.OnFetchDataListener<Track> listener) {

    }

    @Override
    public boolean deleteTrack(Track track) {
        return false;
    }

    @Override
    public void addTrackToPlaylist(int playlistId, TrackDataSource.OnQueryDatabaseListener listener, Track track) {
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, TrackDataSource.OnQueryDatabaseListener listener, Track track) {

    }

    @Override
    public void deletePlaylist(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
    }

    @Override
    public void insertPlaylist(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {

    }

    @Override
    public void addTrackToFavorite(Track track, TrackDataSource.OnQueryDatabaseListener listener) {
    }

    @Override
    public void deleteTrackFavorite(Track track, TrackDataSource.OnQueryDatabaseListener listener) {

    }

    @Override
    public void renamePlayList(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {

    }
}
