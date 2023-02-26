package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import com.github.uissd.dontkill.hook.hooker.Hooker;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class PowerKeeperHooker extends PowerKeeperLogSupport implements Hooker {

    private final LoadPackageParam loadPackageParam;

    public PowerKeeperHooker(LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    @Override
    public boolean hook() {
        if (loadPackageParam.packageName.equals("com.miui.powerkeeper")) {
            PowerStateMachineHooker powerStateMachineHooker = new PowerStateMachineHooker(loadPackageParam);
            SleepModeControllerNewHooker sleepModeControllerNewHooker = new SleepModeControllerNewHooker(loadPackageParam);

            boolean success = powerStateMachineHooker.hook();
            success &= sleepModeControllerNewHooker.hook();
            if (success) {
                logger.i("hook PowerKeeper success");
                return true;
            } else {
                logger.e("hook PowerKeeper failed");
            }
        }
        return false;
    }
}
