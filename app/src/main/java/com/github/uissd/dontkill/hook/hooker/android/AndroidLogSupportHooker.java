package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.constants.Android;
import com.github.uissd.dontkill.hook.constants.TagConst;
import com.github.uissd.dontkill.hook.hooker.LogSupportHooker;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public abstract class AndroidLogSupportHooker extends LogSupportHooker {

    public AndroidLogSupportHooker(String targetClass, LoadPackageParam loadPackageParam) {
        super(targetClass, TagConst.ROOT, Android.LOG_FILE, Android.LOGGER, loadPackageParam);
    }
}
