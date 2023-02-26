package com.github.uissd.dontkill.hook;

import com.github.uissd.dontkill.hook.hooker.android.AndroidHooker;
import com.github.uissd.dontkill.hook.hooker.powerkeeper.PowerKeeperHooker;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) {
        new AndroidHooker(loadPackageParam).hook();
        new PowerKeeperHooker(loadPackageParam).hook();
    }
}
