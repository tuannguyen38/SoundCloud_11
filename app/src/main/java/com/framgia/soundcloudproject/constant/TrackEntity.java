package com.framgia.soundcloudproject.constant;

import android.support.annotation.StringDef;

/**
 * Created by Hades on 8/8/2018.
 */

@StringDef({
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
        TrackEntity.PUBLISHER_ALBUM_TITLE}
)

public @interface TrackEntity {

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
    String PUBLISHER_ALBUM_TITLE = "album_title";
}
