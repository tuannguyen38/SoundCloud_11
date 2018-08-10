package com.framgia.soundcloudproject.screen.main;

import android.graphics.PorterDuff;
import android.support.annotation.IntDef;
import android.support.design.widget.TabLayout;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.TabEntity;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        TabLayout.OnTabSelectedListener, SearchView.OnQueryTextListener, View.OnClickListener {

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
    public void setPresenter(MainContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    /**
     * Tablayout on tab selected listener
     *
     * @method onTabSelected
     * @method onTabUnselected
     * @method onTabReselected
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
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
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
     * BottomMusicControl
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_control_play_pause:
                // TODO
                break;
            case R.id.image_control_previous:
                // TODO
                break;
            case R.id.image_control_next:
                // TODO
                break;
            case R.id.constraint_bottom_control:
                // TODO
                break;
        }
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
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
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
    }

    private void openSearchFragment() {
    }

    private void closeSearchFragment() {
    }
}
