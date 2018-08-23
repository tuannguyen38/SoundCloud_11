package com.framgia.soundcloudproject.data.source.remote;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.utils.StringUtil;

/**
 * Created by Hades on 8/21/2018.
 */
public class TrackDownloadManager {

    private static final String FILE_EXTENSION = ".mp3";
    private static final String TRACK_DIRECTORY = "file:///mnt/sdcard/SoundCloud11/";
    private Context mContext;
    private DownloadListener mListener;

    public TrackDownloadManager(Context context, DownloadListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void downloadTrack(Track track) {
        if (mListener == null) return;
        if (track == null || mContext == null) {
            mListener.onDownloadError(mContext.getString(R.string.msg_download_error));
            return;
        }
        DownloadManager downloadManager =
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(StringUtil.formatTrackStreamURL(track.getUri())));
        request.setTitle(track.getTitle());
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationUri(Uri.parse(TRACK_DIRECTORY + track.getTitle() + FILE_EXTENSION));
        downloadManager.enqueue(request);
        mListener.onDownloading();
    }

    public interface DownloadListener {

        void onDownloadError(String error);

        void onDownloading();
    }
}
