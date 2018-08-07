package com.framgia.soundcloudproject.screen.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.soundcloudproject.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void setPresenter(Object presenter) {
        mPresenter = (MainContract.Presenter) presenter;
    }

    private void initViews() {
        ViewPager viewPager = findViewById(R.id.pager);
    }
}
