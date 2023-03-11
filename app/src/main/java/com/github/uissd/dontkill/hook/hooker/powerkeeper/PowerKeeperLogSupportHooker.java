package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import com.github.uissd.dontkill.hook.constants.PowerKeeper;
import com.github.uissd.dontkill.hook.hooker.LogSupportHooker;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public abstract class PowerKeeperLogSupportHooker extends LogSupportHooker {

    public PowerKeeperLogSupportHooker(String targetClass, XC_LoadPackage.LoadPackageParam loadPackageParam) {
        super(targetClass, PowerKeeper.INDENT_SUPPORT_LOGGER, loadPackageParam);
    }
}
