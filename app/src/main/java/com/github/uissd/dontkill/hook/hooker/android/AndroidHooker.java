package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.hooker.Hooker;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class AndroidHooker extends AndroidLogSupport implements Hooker {
    private final LoadPackageParam loadPackageParam;

    public AndroidHooker(LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    @Override
    public boolean hook() {
        if (loadPackageParam.packageName.equals("android")) {
            ActivityManagerServiceHooker activityManagerServiceHooker = new ActivityManagerServiceHooker(loadPackageParam);
            RecentTasksHooker recentTasksHooker = new RecentTasksHooker(loadPackageParam);

            boolean success = activityManagerServiceHooker.hook();
            success &= recentTasksHooker.hook();
            if (success) {
                logger.i("hook Android success");
                return true;
            } else {
                logger.e("hook Android failed");
            }
        }
        return false;
    }
}
