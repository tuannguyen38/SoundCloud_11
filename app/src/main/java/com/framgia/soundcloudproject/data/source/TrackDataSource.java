package com.framgia.soundcloudproject.data.source;

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

        void onFetchDataFailure(Exception exception);
    }
}
