package com.framgia.soundcloudproject.data.model;

import java.util.List;

/**
 * Created by Hades on 8/7/2018.
 */
public class CollectionResponse {

    private String mGenre;
    private String mKind;
    private String mLastUpdated;
    private List<Collection> mCollections;
    private String mQueryUrn;
    private String mNextHref;

    public CollectionResponse() {
    }

    public CollectionResponse(String genre, String kind, String lastUpdated,
                              List<Collection> collections, String queryUrn, String nextHref) {
        mGenre = genre;
        mKind = kind;
        mLastUpdated = lastUpdated;
        mCollections = collections;
        mQueryUrn = queryUrn;
        mNextHref = nextHref;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public List<Collection> getCollections() {
        return mCollections;
    }

    public void setCollections(List<Collection> collections) {
        mCollections = collections;
    }

    public String getQueryUrn() {
        return mQueryUrn;
    }

    public void setQueryUrn(String queryUrn) {
        mQueryUrn = queryUrn;
    }

    public String getNextHref() {
        return mNextHref;
    }

    public void setNextHref(String nextHref) {
        mNextHref = nextHref;
    }
}
