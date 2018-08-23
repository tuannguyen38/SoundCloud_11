package com.framgia.soundcloudproject.screen.main;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.PorterDuff;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.constant.State;
import com.framgia.soundcloudproject.constant.TabEntity;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.source.remote.TrackDownloadManager;
import com.framgia.soundcloudproject.screen.main.playtrack.PlayTrackActivity;
import com.framgia.soundcloudproject.screen.main.search.SearchFragment;
import com.framgia.soundcloudproject.service.TrackPlayerController;
import com.framgia.soundcloudproject.service.TrackService;
import com.framgia.soundcloudproject.widget.DialogManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        TabLayout.OnTabSelectedListener, SearchView.OnQueryTextListener,
        View.OnClickListener, TrackListener {

    private static final int OFF_SCREEN_LIMIT = 3;
    private static final int[] mTabIcons = {
            R.drawable.ic_home, R.drawable.ic_download, R.drawable.ic_library};
    private MainContract.Presenter mPresenter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ConstraintLayout mConstraintBottomControl;
    private ImageView mImvState;
    private ImageView mImvPrevious;
    private ImageView mImvNext;
    private ImageView mImvTrack;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private TrackService mService;
    private boolean mBound;
    private Track mTrack;
    private ProgressBar mProgressBar;
    private MainPagerAdapter mPagerAdapter;
    private SearchFragment mSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        initComponents();
        setupUI();
        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mDownloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, TrackService.class),
                mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            mService = ((TrackService.LocalBinder) service).getService();
            mService.setTrackInfoListener(new TrackPlayerController.TrackInfoListener() {
                @Override
                public void onTrackChanged(Track track) {
                    updateTrackInfo(track);
                }

                @Override
                public void onStateChanged(int state) {
                    updateStateInfo(state);
                }

                @Override
                public void onLoopChanged(int loopType) {
                }

                @Override
                public void onShuffleChanged(int shuffleMode) {
                }
            });
            updateStateInfo(mService.getMediaState());
            updateTrackInfo(mService.getCurrentTrack());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mService.setTrackInfoListener(null);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mDownloadReceiver);
        } catch (Exception e) {
        }
        unbindService(mConnection);
        mBound = false;
    }

    @Override
    protected void onDestroy() {
        if (mService != null) mService.setTrackInfoListener(null);
        super.onDestroy();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * Tablayout on tab selected listener
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.color_accent);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

        switch (tab.getPosition()) {
            case TabEntity.TAB_HOME:
                mToolbarTitle.setText(R.string.title_home);
                break;
            case TabEntity.TAB_DOWNLOAD:
                mToolbarTitle.setText(R.string.title_download);
                break;
            case TabEntity.TAB_LIBRARY:
                mToolbarTitle.setText(R.string.title_library);
                break;
            default:
                mToolbarTitle.setText(R.string.title_home);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int tabIconColor = ContextCompat.getColor(this, R.color.color_white);
        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(this);
        searchMenu.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                hideTabLayout();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                showTabLayout();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Search
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mSearchFragment == null) return false;
        mSearchFragment.submitQueryText(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void showTabLayout() {
        mTabLayout.setVisibility(View.VISIBLE);
        closeSearchFragment();
    }

    @Override
    public void hideTabLayout() {
        mTabLayout.setVisibility(View.GONE);
        openSearchFragment();
    }

    /**
     * Show/hide progress when loading Track
     */
    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateStateInfo(int state) {
        switch (state) {
            case State.PREPARE:
                showLoading();
                break;
            case State.PLAYING:
                hideLoading();
                mImvState.setImageDrawable(getResources()
                        .getDrawable(R.drawable.ic_pause_circle_filled));
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

    @Override
    public void updateTrackInfo(Track track) {
        if (track == null) return;
        mConstraintBottomControl.setVisibility(View.VISIBLE);
        if (track == mTrack) return;
        Glide.with(this).load(track.getArtworkUrl())
                .apply(new RequestOptions()
                        .override(Constant.DEFAULT_ITEM_SIZE)
                        .centerCrop()
                        .error(R.drawable.ic_holder))
                .into(mImvTrack);
        mTvTitle.setText(track.getTitle());
        mTvArtist.setText(track.getPublisherAlbumTitle());
        mTrack = track;
    }

    @Override
    public void showAddToFavoriteSuccess() {
        Toast.makeText(this, getString(R.string.msg_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddToFavoriteError() {
        Toast.makeText(this, getString(R.string.error_track_added_favorite), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemoveFromFavoriteSuccess() {
        Toast.makeText(this, getString(R.string.msg_removed), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRemoveFromFavoriteError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    /**
     * BottomMusicControl
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_control_play_pause:
                if (!mBound) break;
                mService.changeTrackState();
                break;
            case R.id.image_control_previous:
                if (!mBound) break;
                mService.playPrevious();
                break;
            case R.id.image_control_next:
                if (!mBound) break;
                mService.playNext();
                break;
            case R.id.constraint_bottom_control:
                gotoPlayTrackActivity();
                break;
            default:
                break;
        }
    }

    private void gotoPlayTrackActivity() {
        Intent intent = new Intent(this, PlayTrackActivity.class);
        startActivity(intent);
    }

    private void setupEvents() {
        mTabLayout.addOnTabSelectedListener(this);
        mImvState.setOnClickListener(this);
        mImvPrevious.setOnClickListener(this);
        mImvNext.setOnClickListener(this);
        mConstraintBottomControl.setOnClickListener(this);
    }

    private void setupUI() {
        setSupportActionBar(mToolbar);
        mPresenter = new MainPresenter(this);
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(OFF_SCREEN_LIMIT);
        mToolbarTitle.setText(R.string.title_home);
        setupTabIcons();
    }

    private void setupTabIcons() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(mTabIcons[i]);
        }
    }

    private void initComponents() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tab_layout);
        mToolbar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.text_toolbar_title);
        mImvState = findViewById(R.id.image_control_play_pause);
        mImvNext = findViewById(R.id.image_control_next);
        mImvPrevious = findViewById(R.id.image_control_previous);
        mImvTrack = findViewById(R.id.image_control_track);
        mTvTitle = findViewById(R.id.text_control_title);
        mTvArtist = findViewById(R.id.text_control_artist);
        mConstraintBottomControl = findViewById(R.id.constraint_bottom_control);
        mProgressBar = findViewById(R.id.progress_loading);
    }

    private void openSearchFragment() {
        mSearchFragment = SearchFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.constrain_layout_main, mSearchFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void closeSearchFragment() {
        getSupportFragmentManager().popBackStack();
    }

    /**
     * TrackListener
     */
    @Override
    public void onPlayTrack(int position, List<Track> tracks) {
        if (tracks == null) return;
        handlePlayTracks(position, tracks.toArray(new Track[tracks.size()]));
    }

    @Override
    public void onAddToNextUp(Track track) {
        if (mTrack == null) {
            mService.playTrackAtPosition(0, track);
        } else if (mBound) {
            mService.addToNextUp(track);
        }
    }

    @Override
    public void onAddToPlaylist(Track track) {
        DialogManager dialogManager = new DialogManager(this);
        dialogManager.showAddToPlaylistDialog(this, track);
    }

    @Override
    public void onAddToFavorite(Track track) {
        mPresenter.addToFavorite(track);
    }

    @Override
    public void onDownloadTrack(Track track) {
        new TrackDownloadManager(this, new TrackDownloadManager.DownloadListener() {
            @Override
            public void onDownloadError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloading() {
                Toast.makeText(MainActivity.this,
                        getString(R.string.msg_downloading), Toast.LENGTH_SHORT).show();
            }
        }).downloadTrack(track);
    }

    @Override
    public void onRemoveTrackFromFavorite(Track track) {
        mPresenter.removeFromFavorite(track);
    }

    private void handlePlayTracks(int position, Track... tracks) {
        if (mService == null) return;
        mService.playTrackAtPosition(position, tracks);
        startService(new Intent(this, TrackService.class));
        startActivity(new Intent(this, PlayTrackActivity.class));
    }

    private BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mPagerAdapter != null) {
                mPagerAdapter.updateDownloadData();
            }
        }
    };
}
