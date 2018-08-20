package com.framgia.soundcloudproject.constant;

import android.support.annotation.IntDef;

/**
 * Created by Hades on 8/17/2018.
 */
@IntDef({ShuffleMode.ON,
        ShuffleMode.OFF}
)
public @interface ShuffleMode {
    int OFF = 0;
    int ON = 1;
}
