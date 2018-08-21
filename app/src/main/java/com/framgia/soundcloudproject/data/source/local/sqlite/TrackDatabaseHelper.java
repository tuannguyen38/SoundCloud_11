package com.framgia.soundcloudproject.data.source.local.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.constant.TrackEntity;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/10/2018.
 */
public class TrackDatabaseHelper extends SQLiteOpenHelper {
    public static final String DOT = ".";
    public static final String EQUAL = " = ";
    public static final String EQUAL_CONDITION = " = ? ";
    public static final String LIKE_CONDITION = " LIKE ?";
    public static final String INNER_JOIN = " INNER JOIN ";
    public static final String AND = " AND ";
    public static final String ON = " ON ";
    public static final String WHERE = " WHERE ";
    public static final String SELECT_ALL = "SELECT * FROM ";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "track.db";

    private static final String SQL_CREATE_TRACK_ENTRIES =
            "CREATE TABLE " + TrackEntry.TABLE_NAME_TRACK + "(" +
                    TrackEntity.ID + " INTEGER NOT NULL PRIMARY KEY, " +
                    TrackEntity.TITLE + " TEXT, " +
                    TrackEntity.ARTWORK_URL + " TEXT, " +
                    TrackEntity.DESCRIPTION + " TEXT, " +
                    TrackEntity.DOWNLOAD_COUNT + " INTERGER, " +
                    TrackEntity.DOWNLOADABLE + " INTEGER, " +
                    TrackEntity.FULL_DURATION + " INTEGER, " +
                    TrackEntity.GENRE + " TEXT, " +
                    TrackEntity.LIKE_COUNT + " INTEGER, " +
                    TrackEntity.URI + " TEXT, " +
                    TrackEntity.PUBLISHER_ARTIST + " TEXT);";

    private static final String SQL_CREATE_PLAYLIST_ENTRIES =
            "CREATE TABLE " + TrackEntry.TABLE_NAME_PLAYLIST + "(" +
                    TrackEntry.COLUMN_NAME_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TrackEntry.COLUMN_NAME_PLAYLIST + " TEXT NOT NULL);";

    private static final String SQL_CREATE_PLAYLIST_TRACK_ENTRIES =
            "CREATE TABLE " + TrackEntry.TABLE_NAME_PLAYLIST_TRACK + "(" +
                    TrackEntry.COLUMN_NAME_PLAYLIST_ID + " INTEGER NOT NULL, " +
                    TrackEntity.ID + " INTERGER NOT NULL);";

    private static final String SQL_CREATE_FAVORITE_ENTRIES =
            "CREATE TABLE " + TrackEntry.TABLE_NAME_FAVORITE + "(" +
                    TrackEntity.ID + " INTEGER NOT NULL)";

    private static TrackDatabaseHelper sTrackDatabaseHelper;

    public static TrackDatabaseHelper getInstance(@NonNull Context context) {
        if (sTrackDatabaseHelper == null) {
            sTrackDatabaseHelper = new TrackDatabaseHelper(context);
        }
        return sTrackDatabaseHelper;
    }

    private TrackDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRACK_ENTRIES);
        db.execSQL(SQL_CREATE_PLAYLIST_ENTRIES);
        db.execSQL(SQL_CREATE_PLAYLIST_TRACK_ENTRIES);
        db.execSQL(SQL_CREATE_FAVORITE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertTrack(Track track) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TrackEntity.ID, track.getId());
        values.put(TrackEntity.TITLE, track.getTitle());
        values.put(TrackEntity.ARTWORK_URL, track.getArtworkUrl());
        values.put(TrackEntity.DESCRIPTION, track.getDescription());
        values.put(TrackEntity.DOWNLOAD_COUNT, track.getDownloadCount());
        if (track.isDownloadable()) {
            values.put(TrackEntity.DOWNLOADABLE, 1);
        } else {
            values.put(TrackEntity.DOWNLOADABLE, 0);
        }
        values.put(TrackEntity.FULL_DURATION, track.getFullDuration());
        values.put(TrackEntity.GENRE, track.getGenre());
        values.put(TrackEntity.LIKE_COUNT, track.getLikesCount());
        values.put(TrackEntity.URI, track.getUri());
        values.put(TrackEntity.PUBLISHER_ARTIST, track.getPublisherAlbumTitle());

        database.insert(TrackEntry.TABLE_NAME_TRACK, null, values);
        database.close();
    }

    public List<Track> getAllTrack() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_TRACK,
                null,
                null,
                null,
                null,
                null,
                null);
        List<Track> tracks = new ArrayList<>();
        while (cursor.moveToNext()) {
            tracks.add(parseTrackFromRow(cursor));
        }
        cursor.close();
        database.close();
        return tracks;
    }

    public Track getTrackById(int trackId) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = String.format(Constant.DB_QUERY_EQUAL_SELECTION, TrackEntity.ID);
        String[] selectionArgs = new String[]{String.valueOf(trackId)};
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_TRACK,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Track track = null;
        if (cursor.moveToNext()) {
            track = parseTrackFromRow(cursor);
        }
        cursor.close();
        database.close();
        return track;
    }

    public List<Track> getTrackByTitle(String title) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = String.format(Constant.DB_QUERY_LIKE_SELECTION, TrackEntity.TITLE);
        String[] selectionArgs = new String[]{title};
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_TRACK,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        List<Track> tracks = new ArrayList<>();
        while (cursor.moveToNext()) {
            tracks.add(parseTrackFromRow(cursor));
        }
        cursor.close();
        database.close();
        return tracks;
    }

    public void deleteTrack(Track track) {
        SQLiteDatabase database = getWritableDatabase();
        String selection = String.format(Constant.DB_QUERY_EQUAL_SELECTION, TrackEntity.ID);
        String[] selectionArgs = new String[]{String.valueOf(track.getId())};
        database.delete(TrackEntry.TABLE_NAME_TRACK, selection, selectionArgs);
        database.close();
    }

    public void insertPlaylist(String name) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrackEntry.COLUMN_NAME_PLAYLIST, name);
        database.insert(TrackEntry.TABLE_NAME_PLAYLIST, null, values);
        database.close();
    }

    public Playlist getPlaylistById(int playlistId) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = String.format(Constant.DB_QUERY_EQUAL_SELECTION,
                TrackEntry.COLUMN_NAME_PLAYLIST_ID);
        String[] selectionArgs = new String[]{String.valueOf(playlistId)};
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_PLAYLIST,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        Playlist playlist = null;
        if (cursor.moveToNext()) {
            playlist = new Playlist();
            playlist.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntry.COLUMN_NAME_PLAYLIST_ID)));
            playlist.setName(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntry.COLUMN_NAME_PLAYLIST)));
        }
        cursor.close();
        database.close();
        return playlist;
    }

    public List<Playlist> getAllPlaylist() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_PLAYLIST, null,
                null, null, null, null, null);
        List<Playlist> playlists = new ArrayList<>();
        while (cursor.moveToNext()) {
            Playlist playlist = new Playlist();
            playlist.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntry.COLUMN_NAME_PLAYLIST_ID)));
            playlist.setName(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntry.COLUMN_NAME_PLAYLIST)));
            playlists.add(playlist);
        }
        cursor.close();
        database.close();
        return playlists;
    }

    public int getPlaylistsCount() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_PLAYLIST, null,
                null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public void updatePlaylist(Playlist playlist) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrackEntry.COLUMN_NAME_PLAYLIST, playlist.getName());
        database.update(TrackEntry.TABLE_NAME_PLAYLIST,
                values,
                TrackEntry.COLUMN_NAME_PLAYLIST_ID + EQUAL_CONDITION,
                new String[]{String.valueOf(playlist.getId())});
        database.close();
    }

    public void deletePlaylist(Playlist playlist) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TrackEntry.TABLE_NAME_PLAYLIST_TRACK,
                TrackEntry.COLUMN_NAME_PLAYLIST_ID + EQUAL_CONDITION,
                new String[]{String.valueOf(playlist.getId())});
        database.delete(TrackEntry.TABLE_NAME_PLAYLIST,
                TrackEntry.COLUMN_NAME_PLAYLIST_ID + EQUAL_CONDITION,
                new String[]{String.valueOf(playlist.getId())});
        database.close();
    }

    public void addTrackToPlaylist(Track track, Playlist playlist) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrackEntry.COLUMN_NAME_PLAYLIST_ID, playlist.getId());
        values.put(TrackEntity.ID, track.getId());
        database.insert(TrackEntry.TABLE_NAME_PLAYLIST_TRACK, null, values);
        database.close();
    }

    public List<Track> getAllTracksInPlaylist(Playlist playlist) {
        String query = new StringBuilder()
                .append(SELECT_ALL)
                .append(TrackEntry.TABLE_NAME_PLAYLIST)
                .append(INNER_JOIN)
                .append(TrackEntry.TABLE_NAME_PLAYLIST_TRACK)
                .append(ON)
                .append(TrackEntry.TABLE_NAME_PLAYLIST)
                .append(DOT)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID)
                .append(EQUAL)
                .append(TrackEntry.TABLE_NAME_PLAYLIST_TRACK)
                .append(DOT)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID)
                .append(WHERE)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID)
                .append(EQUAL_CONDITION)
                .toString();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(playlist.getId())});
        List<Track> tracks = new ArrayList<>();
        while (cursor.moveToNext()) {
            tracks.add(parseTrackFromRow(cursor));
        }
        cursor.close();
        database.close();
        return tracks;
    }

    public int getTracksInPlaylistCount(Playlist playlist) {
        String query = new StringBuilder()
                .append(SELECT_ALL)
                .append(TrackEntry.TABLE_NAME_PLAYLIST)
                .append(INNER_JOIN)
                .append(TrackEntry.TABLE_NAME_PLAYLIST_TRACK)
                .append(ON)
                .append(TrackEntry.TABLE_NAME_PLAYLIST)
                .append(DOT)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID)
                .append(EQUAL)
                .append(TrackEntry.TABLE_NAME_PLAYLIST_TRACK)
                .append(DOT)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID)
                .append(WHERE)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID)
                .append(EQUAL_CONDITION)
                .toString();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(playlist.getId())});
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public boolean isTrackInPlaylist(Track track, Playlist playlist) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = new StringBuilder()
                .append(TrackEntity.ID).append(EQUAL_CONDITION)
                .append(AND)
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID).append(EQUAL_CONDITION)
                .toString();
        String[] selectionArgs = new String[]{
                String.valueOf(track.getId()),
                String.valueOf(playlist.getId())};
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_PLAYLIST_TRACK, null,
                selection, selectionArgs, null, null, null);
        boolean check = (cursor.getCount() > 0);
        cursor.close();
        database.close();
        return check;
    }

    public void removeTrackFromPlaylist(Track track, Playlist playlist) {
        SQLiteDatabase database = getWritableDatabase();
        String selection = new StringBuilder()
                .append(TrackEntry.COLUMN_NAME_PLAYLIST_ID).append(EQUAL_CONDITION)
                .append(AND)
                .append(TrackEntity.ID).append(EQUAL_CONDITION)
                .toString();
        String[] selectionArgs = {String.valueOf(track.getId()), String.valueOf(playlist.getId())};
        database.delete(TrackEntry.TABLE_NAME_PLAYLIST_TRACK, selection, selectionArgs);
        database.close();
    }

    public void addTrackToFavorite(Track track) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrackEntity.ID, track.getId());
        database.insert(TrackEntry.TABLE_NAME_FAVORITE, null, values);
        database.close();
    }

    public boolean isTrackInFavorite(Track track) {
        SQLiteDatabase database = getReadableDatabase();
        String selection = String.format(Constant.DB_QUERY_EQUAL_SELECTION, TrackEntity.ID);
        String[] selectionArgs = new String[]{String.valueOf(track.getId())};
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_FAVORITE, null,
                selection, selectionArgs, null, null, null);
        boolean check = (cursor.getCount() > 0);
        cursor.close();
        database.close();
        return check;
    }

    public List<Track> getAllFavoriteTrack() {
        SQLiteDatabase database = getReadableDatabase();
        String query = new StringBuilder()
                .append(SELECT_ALL).append(TrackEntry.TABLE_NAME_TRACK)
                .append(INNER_JOIN).append(TrackEntry.TABLE_NAME_FAVORITE)
                .append(ON)
                .append(TrackEntry.TABLE_NAME_TRACK).append(DOT).append(TrackEntity.ID)
                .append(EQUAL)
                .append(TrackEntry.TABLE_NAME_FAVORITE).append(DOT).append(TrackEntity.ID)
                .toString();
        Cursor cursor = database.rawQuery(query, null);
        List<Track> tracks = new ArrayList<>();
        while (cursor.moveToNext()) {
            tracks.add(parseTrackFromRow(cursor));
        }
        cursor.close();
        database.close();
        return tracks;
    }

    public int getFavoriteTracksCount() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TrackEntry.TABLE_NAME_FAVORITE, null, null, null,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public void removeTrackFromFavorite(Track track) {
        SQLiteDatabase database = getWritableDatabase();
        String selection = String.format(Constant.DB_QUERY_EQUAL_SELECTION, TrackEntity.ID);
        String[] selectionArgs = {String.valueOf(track.getId())};
        database.delete(TrackEntry.TABLE_NAME_FAVORITE, selection, selectionArgs);
        database.close();
    }

    // Parse track from row in track table
    private Track parseTrackFromRow(Cursor cursor) {
        Track track = new Track.Builder().build();
        try {
            track.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntity.ID)));
            track.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntity.TITLE)));
            track.setArtworkUrl(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntity.ARTWORK_URL)));
            track.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntity.DESCRIPTION)));
            track.setDownloadCount(cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntity.DOWNLOAD_COUNT)));
            track.setFullDuration(cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntity.FULL_DURATION)));
            track.setGenre(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntity.GENRE)));
            track.setLikesCount(cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntity.LIKE_COUNT)));
            track.setUri(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntity.URI)));
            track.setPublisherAlbumTitle(cursor.getString(cursor.getColumnIndexOrThrow(TrackEntity.PUBLISHER_ARTIST)));

            int downloadable = cursor.getInt(cursor.getColumnIndexOrThrow(TrackEntity.DOWNLOADABLE));
            if (downloadable == 1) {
                track.setDownloadable(true);
            } else {
                track.setDownloadable(false);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            track = null;
        }
        return track;
    }

    public static final class TrackEntry {
        static final String TABLE_NAME_TRACK = "tracks";
        static final String TABLE_NAME_PLAYLIST = "playlists";
        static final String COLUMN_NAME_PLAYLIST_ID = "playlist_id";
        static final String COLUMN_NAME_PLAYLIST = "playlist_name";
        static final String TABLE_NAME_PLAYLIST_TRACK = "playlist_track";
        static final String TABLE_NAME_FAVORITE = "favorites";
    }
}
