package com.framgia.soundcloudproject.data.source.local;

import android.content.Context;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;
import com.framgia.soundcloudproject.data.source.local.sqlite.TrackDatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Hades on 8/8/2018.
 */
public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static TrackLocalDataSource sInstance;
    private TrackDatabaseHelper mDatabaseHelper;
    private Context mContext;

    private TrackLocalDataSource(Context context) {
        mContext = context;
        mDatabaseHelper = TrackDatabaseHelper.getInstance(context);
    }

    public static TrackLocalDataSource getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource(context);
        }
    }

    @Override
    public List<Playlist> getPlaylist() {
        return mDatabaseHelper.getAllPlaylist();
    }

    @Override
    public List<Track> getTrackInPlaylist(Playlist playlist) {
        return mDatabaseHelper.getAllTracksInPlaylist(playlist);
    }

    @Override
    public List<Track> getTracksFavorite() {
        return mDatabaseHelper.getAllFavoriteTrack();
    }

    @Override
    public boolean deleteTrack(Track track) {
        mDatabaseHelper.deleteTrack(track);
        return true;
    }

    @Override
    public void addTrackToPlaylist(int playlistId, TrackDataSource.OnQueryDatabaseListener listener, Track track) {
        Playlist playlist = mDatabaseHelper.getPlaylistById(playlistId);
        if (playlist == null) {
            listener.onQuerySuccess(mContext.getString(R.string.error_playlist_not_exist));
        } else {
            mDatabaseHelper.addTrackToPlaylist(track, playlist);
            listener.onQuerySuccess(mContext.getString(R.string.msg_save_changes));
        }
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, TrackDataSource.OnQueryDatabaseListener listener, Track track) {
        Playlist playlist = mDatabaseHelper.getPlaylistById(playlistId);
        if (playlist == null) {
            listener.onQueryFailure(new Exception(mContext.getString(R.string.error_playlist_not_exist)));
            return;
        } else {
            mDatabaseHelper.removeTrackFromPlaylist(track, playlist);
            listener.onQuerySuccess(mContext.getString(R.string.msg_save_changes));
        }
    }

    @Override
    public void deletePlaylist(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
        mDatabaseHelper.deletePlaylist(playlist);
        listener.onQuerySuccess(mContext.getString(R.string.msg_deleted));
    }

    @Override
    public void insertPlaylist(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
        mDatabaseHelper.insertPlaylist(playlist.getName());
        listener.onQuerySuccess(mContext.getString(R.string.msg_added));
    }

    @Override
    public void addTrackToFavorite(Track track, TrackDataSource.OnQueryDatabaseListener listener) {
        mDatabaseHelper.addTrackToFavorite(track);
        listener.onQuerySuccess(mContext.getString(R.string.msg_added));
    }

    @Override
    public void deleteTrackFavorite(Track track, TrackDataSource.OnQueryDatabaseListener listener) {
        mDatabaseHelper.removeTrackFromFavorite(track);
        listener.onQuerySuccess(mContext.getString(R.string.msg_deleted));
    }

    @Override
    public void renamePlayList(Playlist playlist, TrackDataSource.OnQueryDatabaseListener listener) {
        if (playlist == null || mDatabaseHelper.getPlaylistById(playlist.getId()) == null) {
            listener.onQueryFailure(new Exception(mContext.getString(R.string.error_playlist_not_exist)));
        } else {
            mDatabaseHelper.updatePlaylist(playlist);
            listener.onQuerySuccess(mContext.getString(R.string.msg_save_changes));
        }
    }
}
