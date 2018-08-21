package com.framgia.soundcloudproject.data.source.remote;

import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.TrackDataSource;
import com.framgia.soundcloudproject.utils.StringUtil;

/**
 * Created by Hades on 8/8/2018.
 */
public class TrackRemoteDataSource implements TrackDataSource.RemoteDataSource {

    private static TrackRemoteDataSource sInstance;

    public static TrackRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TrackRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getTracksRemote(String genre, int limit, int offSet,
                                TrackDataSource.OnFetchDataListener<Track> listener) {
        new FetchTrackFromUrl(listener)
                .execute(StringUtil.formatTrackURL(genre, limit, offSet));
    }

    @Override
    public void searchTracksRemote(String name, int offSet,
                                   TrackDataSource.OnFetchDataListener<Track> listener) {
        new SearchTrackFromUrl(listener)
                .execute(StringUtil.formatSearchTrackURL(name, offSet));
    }
}
