package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import com.github.uissd.dontkill.hook.hooker.Hooker;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class PowerKeeperHooker implements Hooker {

    private final LoadPackageParam loadPackageParam;

    public PowerKeeperHooker(LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    @Override
    public boolean hook() {
        if (loadPackageParam.packageName.equals("com.miui.powerkeeper")) {
            return new PowerKeeperHookerImpl(loadPackageParam).hook();
        }
        return false;
    }

    static class PowerKeeperHookerImpl extends PowerKeeperLogSupport implements Hooker {

        private final LoadPackageParam loadPackageParam;

        public PowerKeeperHookerImpl(LoadPackageParam loadPackageParam) {
            this.loadPackageParam = loadPackageParam;
        }

        @Override
        public boolean hook() {
            PowerStateMachineHooker powerStateMachineHooker = new PowerStateMachineHooker(loadPackageParam);
            SleepModeControllerNewHooker sleepModeControllerNewHooker = new SleepModeControllerNewHooker(loadPackageParam);

            boolean success = powerStateMachineHooker.hook();
            success &= sleepModeControllerNewHooker.hook();
            if (success) {
                logger.i("hook PowerKeeper success");
            } else {
                logger.e("hook PowerKeeper failed");
            }
            return success;
        }
    }
}
