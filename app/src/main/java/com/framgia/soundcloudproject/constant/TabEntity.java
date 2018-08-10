package com.framgia.soundcloudproject.constant;

import android.support.annotation.IntDef;

/**
 * Created by Hades on 8/10/2018.
 */

@IntDef({
        TabEntity.TAB_HOME,
        TabEntity.TAB_DOWNLOAD,
        TabEntity.TAB_LIBRARY,}
)

public @interface TabEntity {
    int TAB_HOME = 0;
    int TAB_DOWNLOAD = 1;
    int TAB_LIBRARY = 2;
}
