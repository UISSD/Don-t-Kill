package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import android.content.Context;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.MethodConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class PowerStateMachineHooker extends PowerKeeperLogSupportHooker {

    public PowerStateMachineHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.POWER_STATE_MACHINE, loadPackageParam);
    }

    @Override
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{
                new HookParam(
                        MethodConst.CLEAR_UNACTIVE_APPS,
                        param -> param.setResult(null),
                        null,
                        Context.class
                ),
                new HookParam(
                        MethodConst.CLEAR_APP_WHEN_SCREEN_OFF_TIME_OUT,
                        param -> param.setResult(null),
                        null
                ),
                new HookParam(
                        MethodConst.CLEAR_APP_WHEN_SCREEN_OFF_TIME_OUT_IN_NIGHT,
                        param -> param.setResult(null),
                        null
                )
        };
    }
}
