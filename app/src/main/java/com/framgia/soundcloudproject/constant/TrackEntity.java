package com.framgia.soundcloudproject.constant;

import android.support.annotation.StringDef;

/**
 * Created by Hades on 8/8/2018.
 */

@StringDef({
        TrackEntity.COLLECTION,
        TrackEntity.USER,
        TrackEntity.AVATAR_URL,
        TrackEntity.TRACK,
        TrackEntity.ARTWORK_URL,
        TrackEntity.DESCRIPTION,
        TrackEntity.DOWNLOADABLE,
        TrackEntity.DOWNLOAD_URL,
        TrackEntity.DOWNLOAD_COUNT,
        TrackEntity.FULL_DURATION,
        TrackEntity.GENRE,
        TrackEntity.ID,
        TrackEntity.LIKE_COUNT,
        TrackEntity.TITLE,
        TrackEntity.URI,
        TrackEntity.PUBLISHER_METADATA,
        TrackEntity.PUBLISHER_ARTIST}
)

public @interface TrackEntity {

    String COLLECTION = "collection";
    String USER = "user";
    String AVATAR_URL = "avatar_url";
    String TRACK = "track";
    String ARTWORK_URL = "artwork_url";
    String DESCRIPTION = "description";
    String DOWNLOADABLE = "downloadable";
    String DOWNLOAD_COUNT = "download_count";
    String DOWNLOAD_URL = "download_url";
    String FULL_DURATION = "full_duration";
    String GENRE = "genre";
    String ID = "id";
    String LIKE_COUNT = "likes_count";
    String TITLE = "title";
    String URI = "uri";
    String USER_ID = "user_id";
    String DISPLAY_DATE = "display_date";
    String PUBLISHER_METADATA = "publisher_metadata";
    String PUBLISHER_ARTIST = "artist";
}
