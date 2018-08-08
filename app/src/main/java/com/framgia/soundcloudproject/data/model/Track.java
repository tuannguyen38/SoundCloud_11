package com.framgia.soundcloudproject.data.model;

/**
 * Created by Hades on 8/7/2018.
 */

public class Track {

    private String mArtworkUrl;
    private String mDescription;
    private boolean mIsDownloadable;
    private int mDownloadCount;
    private String mDownloadUrl;
    private int mFullDuration;
    private String mGenre;
    private int mId;
    private int mLikesCount;
    private String mPublisherAlbumTitle;
    private String mTitle;
    private String mUri;


    private Track(Builder builder) {

        mArtworkUrl = builder.mArtworkUrl;
        mDescription = builder.mDescription;
        mIsDownloadable = builder.mIsDownloadable;
        mDownloadCount = builder.mDownloadCount;
        mDownloadUrl = builder.mDownloadUrl;
        mFullDuration = builder.mFullDuration;
        mGenre = builder.mGenre;
        mId = builder.mId;
        mLikesCount = builder.mLikesCount;
        mTitle = builder.mTitle;
        mUri = builder.mUri;
        mPublisherAlbumTitle = builder.mPublisherAlbumTitle;

    }


    public static class Builder {

        private String mArtworkUrl;
        private String mDescription;
        private boolean mIsDownloadable;
        private int mDownloadCount;
        private String mDownloadUrl;
        private int mFullDuration;
        private String mGenre;
        private int mId;
        private int mLikesCount;
        private String mPublisherAlbumTitle;
        private String mTitle;
        private String mUri;

        public Builder withArtworkUrl(String artworkUrl) {
            this.mArtworkUrl = artworkUrl;
            return this;
        }

        public Builder withDescription(String description) {
            this.mDescription = description;
            return this;
        }

        public Builder withDownloadable(boolean downloadable) {
            this.mIsDownloadable = downloadable;
            return this;
        }

        public Builder withDownloadCount(int downloadCount) {
            this.mDownloadCount = downloadCount;
            return this;
        }

        public Builder withDownloadUrl(String downloadUrl) {
            this.mDownloadUrl = downloadUrl;
            return this;
        }

        public Builder withFullDuration(int fullDuration) {
            this.mFullDuration = fullDuration;
            return this;
        }

        public Builder withGenre(String genre) {
            this.mGenre = genre;
            return this;
        }

        public Builder withId(int id) {
            this.mId = id;
            return this;
        }

        public Builder withLikesCount(int likesCount) {
            this.mLikesCount = likesCount;
            return this;
        }

        public Builder withTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder withUri(String uri) {
            this.mUri = uri;
            return this;
        }

        public Builder withPublisherAlbumTitle(String title) {
            this.mPublisherAlbumTitle = title;
            return this;
        }

        public Track build() {
            return new Track(this);
        }
    }


    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDownloadable() {
        return mIsDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mIsDownloadable = downloadable;
    }

    public int getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        mDownloadCount = downloadCount;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public int getFullDuration() {
        return mFullDuration;
    }

    public void setFullDuration(int fullDuration) {
        mFullDuration = fullDuration;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public String getPublisherAlbumTitle() {
        return mPublisherAlbumTitle;
    }

    public void setPublisherAlbumTitle(String publisherAlbumTitle) {
        mPublisherAlbumTitle = publisherAlbumTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }
}
