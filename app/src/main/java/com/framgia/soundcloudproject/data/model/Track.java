package com.framgia.soundcloudproject.data.model;

/**
 * Created by Hades on 8/7/2018.
 */

public class Track {

    private String mArtworkUrl;
    private boolean mIsCommentable;
    private int mCommentCount;
    private String mCreatedAt;
    private String mDescription;
    private boolean mIsDownloadable;
    private int mDownloadCount;
    private String mDownloadUrl;
    private int mDuration;
    private int mFullDuration;
    private String mEmbeddableBy;
    private String mGenre;
    private boolean mIsHasDownloadsLeft;
    private int mId;
    private String mKind;
    private String mLabelName;
    private String mLastModified;
    private String mLicense;
    private int mLikesCount;
    private String mPermalink;
    private String mPermalinkUrl;
    private int mPlaybackCount;
    private boolean mIsPublic;
    private PublisherMetadata mPublisherMetadata;
    private String mPurchaseTitle;
    private String mPurchaseUrl;
    private int mReleaseDate;
    private int mRepostsCount;
    private String mSecretToken;
    private String mSharing;
    private String mState;
    private boolean mIsStreamable;
    private String mTagList;
    private String mTitle;
    private String mUri;
    private String mUrn;
    private int mUserId;
    private String mVisuals;
    private String mWaveformUrl;
    private String mDisplayDate;
    private String mMonetizationModel;
    private String mPolicy;
    private User mUser;

    public Track() {
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public boolean isCommentable() {
        return mIsCommentable;
    }

    public void setCommentable(boolean commentable) {
        mIsCommentable = commentable;
    }

    public int getCommentCount() {
        return mCommentCount;
    }

    public void setCommentCount(int commentCount) {
        mCommentCount = commentCount;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
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

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getFullDuration() {
        return mFullDuration;
    }

    public void setFullDuration(int fullDuration) {
        mFullDuration = fullDuration;
    }

    public String getEmbeddableBy() {
        return mEmbeddableBy;
    }

    public void setEmbeddableBy(String embeddableBy) {
        mEmbeddableBy = embeddableBy;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public boolean isHasDownloadsLeft() {
        return mIsHasDownloadsLeft;
    }

    public void setHasDownloadsLeft(boolean hasDownloadsLeft) {
        mIsHasDownloadsLeft = hasDownloadsLeft;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public Object getLabelName() {
        return mLabelName;
    }

    public void setLabelName(String labelName) {
        mLabelName = labelName;
    }

    public String getLastModified() {
        return mLastModified;
    }

    public void setLastModified(String lastModified) {
        mLastModified = lastModified;
    }

    public String getLicense() {
        return mLicense;
    }

    public void setLicense(String license) {
        mLicense = license;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public String getPermalink() {
        return mPermalink;
    }

    public void setPermalink(String permalink) {
        mPermalink = permalink;
    }

    public String getPermalinkUrl() {
        return mPermalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        mPermalinkUrl = permalinkUrl;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public boolean isPublic() {
        return mIsPublic;
    }

    public void setPublic(boolean aPublic) {
        mIsPublic = aPublic;
    }

    public PublisherMetadata getPublisherMetadata() {
        return mPublisherMetadata;
    }

    public void setPublisherMetadata(PublisherMetadata publisherMetadata) {
        mPublisherMetadata = publisherMetadata;
    }

    public String getPurchaseTitle() {
        return mPurchaseTitle;
    }

    public void setPurchaseTitle(String purchaseTitle) {
        mPurchaseTitle = purchaseTitle;
    }

    public String getPurchaseUrl() {
        return mPurchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        mPurchaseUrl = purchaseUrl;
    }

    public int getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        mReleaseDate = releaseDate;
    }

    public int getRepostsCount() {
        return mRepostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        mRepostsCount = repostsCount;
    }

    public String getSecretToken() {
        return mSecretToken;
    }

    public void setSecretToken(String secretToken) {
        mSecretToken = secretToken;
    }

    public String getSharing() {
        return mSharing;
    }

    public void setSharing(String sharing) {
        mSharing = sharing;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public boolean isStreamable() {
        return mIsStreamable;
    }

    public void setStreamable(boolean streamable) {
        mIsStreamable = streamable;
    }

    public String getTagList() {
        return mTagList;
    }

    public void setTagList(String tagList) {
        mTagList = tagList;
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

    public String getUrn() {
        return mUrn;
    }

    public void setUrn(String urn) {
        mUrn = urn;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getVisuals() {
        return mVisuals;
    }

    public void setVisuals(String visuals) {
        mVisuals = visuals;
    }

    public String getWaveformUrl() {
        return mWaveformUrl;
    }

    public void setWaveformUrl(String waveformUrl) {
        mWaveformUrl = waveformUrl;
    }

    public String getDisplayDate() {
        return mDisplayDate;
    }

    public void setDisplayDate(String displayDate) {
        mDisplayDate = displayDate;
    }

    public String getMonetizationModel() {
        return mMonetizationModel;
    }

    public void setMonetizationModel(String monetizationModel) {
        mMonetizationModel = monetizationModel;
    }

    public String getPolicy() {
        return mPolicy;
    }

    public void setPolicy(String policy) {
        mPolicy = policy;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }
}
