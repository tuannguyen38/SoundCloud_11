package com.framgia.soundcloudproject;

import android.app.Application;

import com.framgia.soundcloudproject.data.source.local.TrackLocalDataSource;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TrackLocalDataSource.init(this);
    }
}
