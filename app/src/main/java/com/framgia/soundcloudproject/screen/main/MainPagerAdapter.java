package com.framgia.soundcloudproject.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.soundcloudproject.constant.TabEntity;
import com.framgia.soundcloudproject.screen.main.download.DownloadFragment;
import com.framgia.soundcloudproject.screen.main.home.HomeFragment;
import com.framgia.soundcloudproject.screen.main.library.LibraryFragment;

/**
 * Created by Hades on 8/9/2018.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 3;
    private DownloadFragment mDownloadFragment;
    private LibraryFragment mLibraryFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case TabEntity.TAB_HOME:
                return HomeFragment.newInstance();
            case TabEntity.TAB_DOWNLOAD:
                mDownloadFragment = new DownloadFragment();
                return mDownloadFragment;
            case TabEntity.TAB_LIBRARY:
                mLibraryFragment = new LibraryFragment();
                return mLibraryFragment;
            default:
                return HomeFragment.newInstance();
        }
    }

    public void updateFavoriteData() {
        mLibraryFragment.refresh();
    }

    public void updateDownloadData() {
        mDownloadFragment.refresh();
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
