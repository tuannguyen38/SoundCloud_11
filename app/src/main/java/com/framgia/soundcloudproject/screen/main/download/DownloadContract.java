package com.framgia.soundcloudproject.screen.main.download;

import com.framgia.soundcloudproject.data.model.Track;

import java.util.List;

public interface DownloadContract {
    interface Presenter {
        void getOfflineTracks();
    }

    interface View {
        void showTracks(List<Track> tracks);

        void showNoTrackInDevice();

        void showError(String error);
    }
}
