package com.framgia.soundcloudproject.data.model;

/**
 * Created by Hades on 8/7/2018.
 */
public class Collection {

    private Track mTrack;
    private double mScore;

    public Collection() {
    }

    public Collection(Track track, double score) {
        mTrack = track;
        mScore = score;
    }

    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
    }

    public double getScore() {
        return mScore;
    }

    public void setScore(double score) {
        mScore = score;
    }
}
