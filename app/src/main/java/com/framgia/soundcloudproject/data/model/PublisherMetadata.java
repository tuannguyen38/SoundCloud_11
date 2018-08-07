package com.framgia.soundcloudproject.data.model;

/**
 * Created by Hades on 8/7/2018.
 */
public class PublisherMetadata {

    private int mId;
    private String mUrn;
    private String mArtist;
    private boolean mIsContainMusic;
    private String mPublisher;
    private String mIsrc;
    private String mWriterComposer;
    private String mReleaseTitle;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUrn() {
        return mUrn;
    }

    public void setUrn(String urn) {
        mUrn = urn;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public boolean isContainMusic() {
        return mIsContainMusic;
    }

    public void setContainMusic(boolean containMusic) {
        mIsContainMusic = containMusic;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getIsrc() {
        return mIsrc;
    }

    public void setIsrc(String isrc) {
        mIsrc = isrc;
    }

    public String getWriterComposer() {
        return mWriterComposer;
    }

    public void setWriterComposer(String writerComposer) {
        mWriterComposer = writerComposer;
    }

    public String getReleaseTitle() {
        return mReleaseTitle;
    }

    public void setReleaseTitle(String releaseTitle) {
        mReleaseTitle = releaseTitle;
    }
}
