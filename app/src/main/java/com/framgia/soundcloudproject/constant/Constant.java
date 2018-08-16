package com.framgia.soundcloudproject.constant;

/**
 * Created by Hades on 8/7/2018.
 */
public class Constant {

    public static final String BASE_URL = "https://api-v2.soundcloud.com/";
    public static final String MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    public static final String SEARCH_TRACK = "search/tracks?facet=genre&limit=10&linked_partitioning=1&q=";

    public static final String OFFSET = "offset";
    public static final String STREAM = "stream";
    public static final String CLIENT_ID = "client_id";
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String LIMIT = "limit";
    public static final int READ_TIME_OUT = 5000;
    public static final int CONNECT_TIME_OUT = 5000;
    public static final int OFFSET_DEFAULT = 0;
    public static final int LIMIT_DEFAULT = 10;
    public static final String NULL_RESULT = "NULL";
    public static final String TRACK_QUERY_FORMAT = "%s%s%s&%s=%s&%s=%d&%s=%d";
    public static final String SEARCH_QUERY_FORMAT = "%s%s%s&%s=%d&%s=%s";
    public static final String BREAK_LINE = "\n";
    public static final String UNKNOWN = "<unknown>";
    public static final String GENRE_TYPE = "GENRE_TYPE";
}
