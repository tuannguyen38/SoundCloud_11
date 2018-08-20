package com.framgia.soundcloudproject.data.source.remote;

import android.os.AsyncTask;

import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.constant.TrackEntity;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/19/2018.
 */
public class SearchTrackFromUrl extends AsyncTask<String, Void, List<Track>> {

    private TrackDataSource.OnFetchDataListener<Track> mListener;
    private Exception mException;

    public SearchTrackFromUrl(TrackDataSource.OnFetchDataListener<Track> listener) {
        mListener = listener;
    }

    @Override
    protected List<Track> doInBackground(String... strings) {
        String url = strings[0];
        String data;
        try {
            data = getJsonStringFromUrl(url);
            return getTracksFromJson(data);
        } catch (IOException e) {
            mException = e;
        } catch (JSONException e) {
            mException = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Track> tracks) {
        if (mListener == null) return;
        if (mException == null) {
            mListener.onFetchDataSuccess(tracks);
        } else {
            mListener.onFetchDataFailure(mException);
        }
    }

    private List<Track> getTracksFromJson(String data) throws JSONException {
        List<Track> tracks = new ArrayList<>();
        JSONObject object = new JSONObject(data);
        JSONArray jsonArray = object.getJSONArray(TrackEntity.COLLECTION);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            String artworkUrl = jsonObject.optString(TrackEntity.ARTWORK_URL);
            if (artworkUrl.isEmpty()) {
                artworkUrl = jsonObject.getJSONObject(TrackEntity.USER)
                        .optString(TrackEntity.AVATAR_URL);
            }
            String description = jsonObject.optString(TrackEntity.DESCRIPTION);
            boolean downloadable = jsonObject.getBoolean(TrackEntity.DOWNLOADABLE);
            String downloadUrl = jsonObject.optString(TrackEntity.DOWNLOAD_URL);
            int downloadCount = jsonObject.getInt(TrackEntity.DOWNLOAD_COUNT);
            int fullDuration = jsonObject.getInt(TrackEntity.FULL_DURATION);
            String genre = jsonObject.optString(TrackEntity.GENRE);
            int id = jsonObject.getInt(TrackEntity.ID);
            int likeCount = jsonObject.getInt(TrackEntity.LIKE_COUNT);
            String title = jsonObject.optString(TrackEntity.TITLE);
            String uri = jsonObject.optString(TrackEntity.URI);
            JSONObject publisherObject = jsonObject.optJSONObject(TrackEntity.PUBLISHER_METADATA);
            String publisherArtist = null;
            if (publisherObject != null) {
                publisherArtist = publisherObject.optString(TrackEntity.PUBLISHER_ARTIST, Constant.UNKNOWN);
            }

            Track track = new Track.Builder()
                    .withArtworkUrl(artworkUrl)
                    .withDescription(description)
                    .withDownloadable(downloadable)
                    .withDownloadCount(downloadCount)
                    .withDownloadUrl(downloadUrl)
                    .withFullDuration(fullDuration)
                    .withGenre(genre)
                    .withId(id)
                    .withLikesCount(likeCount)
                    .withTitle(title)
                    .withUri(uri)
                    .withPublisherAlbumTitle(publisherArtist)
                    .build();

            tracks.add(track);
        }
        return tracks;
    }

    private String getJsonStringFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(Constant.REQUEST_METHOD_GET);
        connection.setConnectTimeout(Constant.CONNECT_TIME_OUT);
        connection.setReadTimeout(Constant.READ_TIME_OUT);
        connection.setDoOutput(true);
        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(Constant.BREAK_LINE);
        }
        br.close();
        connection.disconnect();
        return sb.toString();
    }

    public void setListener(TrackDataSource.OnFetchDataListener<Track> listener) {
        mListener = listener;
    }
}
