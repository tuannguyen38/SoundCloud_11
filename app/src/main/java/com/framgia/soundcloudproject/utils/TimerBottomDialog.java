package com.framgia.soundcloudproject.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.framgia.soundcloudproject.R;

/**
 * Created by Hades on 8/20/2018.
 */
public class TimerBottomDialog extends BottomSheetDialog implements View.OnClickListener {

    private SeekBar mSeekBar;
    private TextView mTvTimerStart;
    private TextView mTvTimerStop;

    private OnBottomDialogItemClickListener mListener;

    public TimerBottomDialog(@NonNull Context context) {
        super(context);
        View bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom_timer, null);
        setContentView(bottomView);
        mSeekBar = bottomView.findViewById(R.id.seek_bar_timer);
        mTvTimerStart = bottomView.findViewById(R.id.text_timer_start);
        mTvTimerStop = bottomView.findViewById(R.id.text_timer_stop);
        mTvTimerStart.setOnClickListener(this);
        mTvTimerStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener == null) return;
        switch (v.getId()) {
            case R.id.text_timer_start:
                mListener.onTimerStart(mSeekBar.getProgress());
                break;
            case R.id.text_timer_stop:
                mListener.onTimerStop();
                break;
        }
    }

    public void setListener(OnBottomDialogItemClickListener listener) {
        mListener = listener;
    }

    public interface OnBottomDialogItemClickListener {
        void onTimerStart(int position);

        void onTimerStop();
    }
}
