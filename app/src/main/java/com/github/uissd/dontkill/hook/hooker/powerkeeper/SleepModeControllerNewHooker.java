package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.MethodConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class SleepModeControllerNewHooker extends PowerKeeperLogSupportHooker {

    public SleepModeControllerNewHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.SLEEP_MODE_CONTROLLER_NEW, loadPackageParam);
    }

    @Override
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{
                new HookParam(
                        MethodConst.CLEAR_APP,
                        param -> param.setResult(null),
                        null
                )
        };
    }
}
