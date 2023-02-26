package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.components.log.LogSupport;
import com.github.uissd.dontkill.hook.constants.Android;
import com.github.uissd.dontkill.hook.constants.TagConst;

public abstract class AndroidLogSupport extends LogSupport {

    public AndroidLogSupport() {
        super(TagConst.ROOT, Android.LOG_FILE, Android.LOGGER);
    }
}
