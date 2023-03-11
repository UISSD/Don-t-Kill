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
            super("AndroidHookerImpl");
            this.loadPackageParam = loadPackageParam;
        }

        @Override
        public boolean hook() {
            ActivityManagerServiceHooker activityManagerServiceHooker = new ActivityManagerServiceHooker(loadPackageParam);
            boolean success = activityManagerServiceHooker.hook();

            RecentTasksHooker recentTasksHooker = new RecentTasksHooker(loadPackageParam);
            success &= recentTasksHooker.hook();

            PhantomProcessListHooker phantomProcessListHooker = new PhantomProcessListHooker(loadPackageParam);
            success &= phantomProcessListHooker.hook();

            ActivityManagerConstantsHooker activityManagerConstantsHooker = new ActivityManagerConstantsHooker(loadPackageParam);
            success &= activityManagerConstantsHooker.hook();

            if (success) {
                logger.i("hook Android success");
            } else {
                logger.e("hook Android failed");
            }
            return success;
        }
    }
}
