package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.components.log.LogSupport;
import com.github.uissd.dontkill.hook.constants.Android;

public abstract class AndroidLogSupport extends LogSupport {

    public AndroidLogSupport(String identity) {
        super(identity, Android.INDENT_SUPPORT_LOGGER);
    }
}
