package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.hooker.Hooker;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class AndroidHooker implements Hooker {
    private final LoadPackageParam loadPackageParam;

    public AndroidHooker(LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    @Override
    public boolean hook() {
        if (loadPackageParam.packageName.equals("android")) {
            return new AndroidHookerImpl(loadPackageParam).hook();
        }
        return false;
    }

    static class AndroidHookerImpl extends AndroidLogSupport implements Hooker {

        private final LoadPackageParam loadPackageParam;

        public AndroidHookerImpl(LoadPackageParam loadPackageParam) {
            this.loadPackageParam = loadPackageParam;
        }

        @Override
        public boolean hook() {
            ActivityManagerServiceHooker activityManagerServiceHooker = new ActivityManagerServiceHooker(loadPackageParam);
            RecentTasksHooker recentTasksHooker = new RecentTasksHooker(loadPackageParam);
            PhantomProcessListHooker phantomProcessListHooker = new PhantomProcessListHooker(loadPackageParam);

            boolean success = activityManagerServiceHooker.hook();
            success &= recentTasksHooker.hook();
            success &= phantomProcessListHooker.hook();
            if (success) {
                logger.i("hook Android success");
            } else {
                logger.e("hook Android failed");
            }
            return success;
        }
    }
}
