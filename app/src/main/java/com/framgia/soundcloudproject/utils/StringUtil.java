package com.framgia.soundcloudproject.utils;

import com.framgia.soundcloudproject.BuildConfig;
import com.framgia.soundcloudproject.constant.Constant;

/**
 * Created by Hades on 8/8/2018.
 */
public class StringUtil {

    public static String formatTrackURL(String genre, int limit, int offSet) {
        return String.format(Constant.TRACK_QUERY_FORMAT, Constant.BASE_URL,
                Constant.MUSIC_GENRE, genre, Constant.CLIENT_ID,
                BuildConfig.API_KEY, Constant.LIMIT, limit, Constant.OFFSET, offSet);
    }

    public static String formatSearchTrackURL(String name, int offSet) {
        return String.format(Constant.SEARCH_QUERY_FORMAT, Constant.BASE_URL,
                Constant.SEARCH_TRACK, name, Constant.OFFSET, offSet,
                Constant.CLIENT_ID, BuildConfig.API_KEY);
    }
}
