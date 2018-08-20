package com.framgia.soundcloudproject.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;
import com.framgia.soundcloudproject.data.source.local.sqlite.TrackDatabaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/8/2018.
 */
public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {

    private static final String SORT_ORDER_ASCENDING = "ASC";
    private static TrackLocalDataSource sInstance;
    private Context mContext;
    private TrackDatabaseHelper mDatabaseHelper;

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
    public void getOfflineTracks(TrackDataSource.OnFetchDataListener<Track> listener) {
        List<Track> tracks = new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        String sortByTitleAscending = MediaStore.Audio.Media.TITLE + " " + SORT_ORDER_ASCENDING;
        Cursor cursor = resolver.query(uri,
                projection,
                null,
                null,
                sortByTitleAscending);

        if (cursor == null) {
            listener.onFetchDataFailure(new Exception(mContext.getString(R.string.error_load_data)));
            return;
        }
        while (cursor.moveToNext()) {
            Track track = parseTrackFromRow(cursor);
            tracks.add(track);
        }
        cursor.close();
        listener.onFetchDataSuccess(tracks);
    }

    @Override
    public boolean deleteOfflineTrack(Track track) {
        File file = new File(track.getUri());
        return file.delete();
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
        if (mDatabaseHelper.getTrackById(track.getId()) == null) {
            mDatabaseHelper.insertTrack(track);
        }
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

    private Track parseTrackFromRow(Cursor cursor) {
        Track track = new Track.Builder().build();
        track.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        track.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        track.setPublisherAlbumTitle(cursor.getString(
                cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        track.setUri(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        track.setFullDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
        return track;
    }
}
