package com.framgia.soundcloudproject.screen.main.playtrack;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.constant.LoopType;
import com.framgia.soundcloudproject.constant.ShuffleMode;
import com.framgia.soundcloudproject.constant.State;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;
import com.framgia.soundcloudproject.data.source.remote.TrackDownloadManager;
import com.framgia.soundcloudproject.screen.main.MainActivity;
import com.framgia.soundcloudproject.service.TrackPlayerController;
import com.framgia.soundcloudproject.service.TrackService;
import com.framgia.soundcloudproject.utils.StringUtil;
import com.framgia.soundcloudproject.utils.TimerBottomDialog;

/**
 * Created by Hades on 8/13/2018.
 */
public class PlayTrackActivity extends AppCompatActivity implements PlayTrackContract.View,
        View.OnClickListener, TrackPlayerController.TrackInfoListener,
        NextUpAdapter.OnTrackItemClickListener {

    private static final long DELAY_MILLIS = 500;
    private ImageView mImvState;
    private ImageView mImvPrevious;
    private ImageView mImvNext;
    private ImageView mImvLoop;
    private ImageView mImvShuffle;
    private TextView mTvStart;
    private TextView mTvEnd;
    private SeekBar mSeekBar;
    private ImageView mImvTimer;
    private ImageView mImvFavorite;
    private ImageView mImvDownload;
    private ImageView mImvDescription;
    private ProgressBar mProgressLoading;
    private ImageView mImvTrack;
    private Track mCurrentTrack;
    private int mUserSelectedPosition = 0;
    private boolean mBound;
    private TrackService mService;
    private PlayTrackContract.Presenter mPresenter;
    private ImageView mImvNextup;
    private ImageView mImvClose;
    private ImageView mImvBackground;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private NextUpFragment mNextUpFragment;
    private TrackRepository mTrackRepository;
    private int mDisplayWidth;
    private int mDisplayHeight;


    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    protected void onStart() {
        super.onStart();
        Intent trackIntent = new Intent(this, TrackService.class);
        bindService(trackIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_track);
        mPresenter = new PlayTrackPresenter(this);
        initComponents();
        setupEvents();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    public void onClick(View v) {
        if (mService == null) return;
        switch (v.getId()) {
            case R.id.image_track_state:
                mService.changeTrackState();
                break;
            case R.id.image_track_previous:
                mService.playPrevious();
                break;
            case R.id.image_track_next:
                mService.playNext();
                break;
            case R.id.image_loop:
                mService.changeLoopType();
                break;
            case R.id.image_shuffle:
                mService.changeShuffleState();
                break;
            case R.id.image_timer:
                handleTimer();
                break;
            case R.id.image_favorite:
                handleAddToFavorite();
                break;
            case R.id.image_download:
                handleDownload();
                break;
            case R.id.image_description:
                handleShowDescription();
                break;
            case R.id.image_next_up:
                handleShowNextUp();
                break;
            case R.id.image_close:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void handleShowDescription() {
        Toast.makeText(PlayTrackActivity.this, mCurrentTrack.getDescription(), Toast.LENGTH_SHORT).show();
    }

    private void handleTimer() {
        final TimerBottomDialog mDialog = new TimerBottomDialog(this);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setListener(new TimerBottomDialog.OnBottomDialogItemClickListener() {
            @Override
            public void onTimerStart(int position) {
                long time = getResources().getIntArray(R.array.timer)[position];
                mService.startTimer(time);
                Toast.makeText(PlayTrackActivity.this, getString(R.string.msg_sleeping, time), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }

            @Override
            public void onTimerStop() {
                mService.cancelTimer();
                Toast.makeText(PlayTrackActivity.this, R.string.msg_cancel_sleeping, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void handleDownload() {
        // TODO CHECK IS DOWNLOADED
        new TrackDownloadManager(this, new TrackDownloadManager.DownloadListener() {
            @Override
            public void onDownloadError(String error) {
                Toast.makeText(PlayTrackActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloading() {
                Toast.makeText(PlayTrackActivity.this, getString(R.string.msg_downloading), Toast.LENGTH_SHORT).show();
            }
        }).downloadTrack(mCurrentTrack);
    }

    private void handleAddToFavorite() {
        if (mCurrentTrack == null) return;
        mTrackRepository = TrackRepository.getInstance();
        if (!mTrackRepository.isTrackInFavorite(mCurrentTrack)) {
            mTrackRepository.addTrackToFavorite(mCurrentTrack,
                    new TrackDataSource.OnQueryDatabaseListener() {
                        @Override
                        public void onQuerySuccess(String msg) {
                            mImvFavorite.setImageResource(R.drawable.ic_favorite_filled);
                            Toast.makeText(PlayTrackActivity.this,
                                    R.string.msg_added, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onQueryFailure(Exception e) {
                            mImvFavorite.setImageResource(R.drawable.ic_favorite_border);
                            Toast.makeText(PlayTrackActivity.this,
                                    R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            mTrackRepository.deleteTrackFavorite(mCurrentTrack,
                    new TrackDataSource.OnQueryDatabaseListener() {
                        @Override
                        public void onQuerySuccess(String msg) {
                            mImvFavorite.setImageResource(R.drawable.ic_favorite_border);
                            Toast.makeText(PlayTrackActivity.this,
                                    R.string.msg_removed, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onQueryFailure(Exception e) {
                            mImvFavorite.setImageResource(R.drawable.ic_favorite_filled);
                            Toast.makeText(PlayTrackActivity.this,
                                    R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void handleShowNextUp() {
        mNextUpFragment = new NextUpFragment(mService.getListTrack(),
                mService.getCurrentTrackPosition());
        mNextUpFragment.show(getSupportFragmentManager(), mNextUpFragment.getTag());
    }

    /**
     * @TrackInfoListener :
     * Track changed
     * State changed
     * Loop changed
     * Shuffle changed
     */

    @Override
    public void onTrackChanged(Track track) {
        mCurrentTrack = track;
        updateTrackInfo();
    }

    @Override
    public void onStateChanged(int state) {
        updateStateInfo(state);
    }

    @Override
    public void onLoopChanged(int loopType) {
        updateLoopType(loopType);
    }

    @Override
    public void onShuffleChanged(int shuffleMode) {
        updateShuffleMode(shuffleMode);
    }

    /**
     * show/hide loading when track buffering
     */
    @Override
    public void showLoading() {
        mProgressLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressLoading.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(PlayTrackContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBound = true;
            mService = ((TrackService.LocalBinder) service).getService();
            mService.setTrackInfoListener(PlayTrackActivity.this);
            mCurrentTrack = mService.getCurrentTrack();

            updateTrackInfo();
            updateStateInfo(mService.getMediaState());
            updateLoopType(mService.getLoopType());
            updateShuffleMode(mService.getShuffleMode());
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            mService.setTrackInfoListener(null);
        }
    };

    private void initComponents() {
        mImvState = findViewById(R.id.image_track_state);
        mImvPrevious = findViewById(R.id.image_track_previous);
        mImvNext = findViewById(R.id.image_track_next);
        mImvLoop = findViewById(R.id.image_loop);
        mImvShuffle = findViewById(R.id.image_shuffle);
        mTvStart = findViewById(R.id.text_track_start);
        mTvEnd = findViewById(R.id.text_track_end);
        mSeekBar = findViewById(R.id.seek_bar_play_track);
        mImvTimer = findViewById(R.id.image_timer);
        mImvFavorite = findViewById(R.id.image_favorite);
        mImvDownload = findViewById(R.id.image_download);
        mImvDescription = findViewById(R.id.image_description);
        mImvTrack = findViewById(R.id.image_track);
        mProgressLoading = findViewById(R.id.progress_loading);
        mImvNextup = findViewById(R.id.image_next_up);
        mImvClose = findViewById(R.id.image_close);
        mImvBackground = findViewById(R.id.image_background);
        mTvTitle = findViewById(R.id.text_track_title);
        mTvArtist = findViewById(R.id.text_track_artist);

        mDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        mDisplayHeight = getResources().getDisplayMetrics().heightPixels;
    }

    private void setupEvents() {
        mImvState.setOnClickListener(this);
        mImvPrevious.setOnClickListener(this);
        mImvNext.setOnClickListener(this);
        mImvLoop.setOnClickListener(this);
        mImvShuffle.setOnClickListener(this);
        mImvTimer.setOnClickListener(this);
        mImvFavorite.setOnClickListener(this);
        mImvDownload.setOnClickListener(this);
        mImvDescription.setOnClickListener(this);
        mImvNextup.setOnClickListener(this);
        mImvClose.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(mSeekbarChangeListener);
    }

    public void updateTrackInfo() {
        if (mCurrentTrack == null || mService == null) return;
        mTvTitle.setText(mCurrentTrack.getTitle());
        mTvArtist.setText(mCurrentTrack.getPublisherAlbumTitle());
        mTvEnd.setText(StringUtil.convertMilisecondToTimer(mCurrentTrack.getFullDuration()));
        Glide.with(this).load(mCurrentTrack.getArtworkUrl())
                .apply(RequestOptions.circleCropTransform().override(mDisplayWidth).error(R.drawable.ic_holder))
                .into(mImvTrack);
        Glide.with(this)
                .load(mCurrentTrack.getArtworkUrl())
                .apply(new RequestOptions().override(
                        mDisplayWidth, mDisplayHeight).error(R.drawable.ic_holder))
                .into(mImvBackground);
        updateFavoriteState();
    }

    public void updateFavoriteState() {
        if (TrackRepository.getInstance().isTrackInFavorite(mCurrentTrack)) {
            mImvFavorite.setImageResource(R.drawable.ic_favorite_filled);
        } else {
            mImvFavorite.setImageResource(R.drawable.ic_favorite_border);
        }
    }

    private SeekBar.OnSeekBarChangeListener mSeekbarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mUserSelectedPosition = progress;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (mBound) {
                mService.actionSeekTo(mUserSelectedPosition);
            }
        }
    };

    private void updateStateInfo(@State int state) {
        switch (state) {
            case State.PREPARE:
                showLoading();
                stopUpdate();
                break;
            case State.PLAYING:
                hideLoading();
                mImvState.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_pause_circle_filled));
                updateSeekbar();
                break;
            case State.PAUSE:
                hideLoading();
                mImvState.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_play_circle_filled));
                break;
            default:
                break;
        }
    }

    private void stopUpdate() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void updateSeekbar() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mSeekBar.setProgress((int) ((double) mService.getCurrentPosition() / mService.getDuration() * 100));
                mTvStart.setText(StringUtil.convertMilisecondToTimer(mService.getCurrentPosition()));
                mHandler.postDelayed(this, DELAY_MILLIS);
            }
        };
        mHandler.post(mRunnable);
    }

    private void updateLoopType(@LoopType int loopType) {
        switch (loopType) {
            case LoopType.NO_LOOP:
                mImvLoop.setImageResource(R.drawable.ic_repeat);
                break;
            case LoopType.LOOP_ONE:
                mImvLoop.setImageResource(R.drawable.ic_repeat_one);
                break;
            case LoopType.LOOP_LIST:
                mImvLoop.setImageResource(R.drawable.ic_repeat_list);
                break;
        }
    }

    private void updateShuffleMode(@ShuffleMode int mode) {
        switch (mode) {
            case ShuffleMode.OFF:
                mImvShuffle.setImageResource(R.drawable.ic_shuffle);
                break;
            case ShuffleMode.ON:
                mImvShuffle.setImageResource(R.drawable.ic_shuffle_on);
                break;
        }
    }

    @Override
    public void onTrackItemClicked(int position) {
        if (mBound) {
            mNextUpFragment.dismiss();
            mService.playTrackAtPosition(position);
        }
    }
}
