package com.framgia.soundcloudproject.widget;

import android.app.Activity;

import com.framgia.soundcloudproject.data.model.Track;

public interface DialogInterface {
    void showMessageDialog(String title, String msg);

    void showAddToPlaylistDialog(Activity activity, Track track);

    void dismissDialog();
}
