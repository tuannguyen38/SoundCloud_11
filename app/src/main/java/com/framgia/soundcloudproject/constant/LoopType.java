package com.framgia.soundcloudproject.constant;

import android.support.annotation.IntDef;

/**
 * Created by Hades on 8/13/2018.
 */
@IntDef({
        LoopType.NO_LOOP,
        LoopType.LOOP_ONE,
        LoopType.LOOP_LIST}
)
public @interface LoopType {
    int NO_LOOP = -1;
    int LOOP_ONE = 0;
    int LOOP_LIST = 1;
}
