package com.framgia.soundcloudproject.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.constant.TabEntity;
import com.framgia.soundcloudproject.screen.main.download.DownloadFragment;
import com.framgia.soundcloudproject.screen.main.home.HomeFragment;
import com.framgia.soundcloudproject.screen.main.library.LibraryFragment;

/**
 * Created by Hades on 8/9/2018.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 3;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case TabEntity.TAB_HOME:
                // Update next by getInstance()
                return new HomeFragment();
            case TabEntity.TAB_DOWNLOAD:
                // Update next by getInstance()
                return new DownloadFragment();
            case TabEntity.TAB_LIBRARY:
                // Update next by getInstance()
                return new LibraryFragment();
            default:
                // Update next by getInstance()
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
