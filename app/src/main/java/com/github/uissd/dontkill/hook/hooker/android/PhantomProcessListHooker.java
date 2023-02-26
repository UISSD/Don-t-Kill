package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.MethodConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class PhantomProcessListHooker extends AndroidLogSupportHooker {

    public PhantomProcessListHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.PHANTOM_PROCESS_LIST, loadPackageParam);
    }

    @Override
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{
                new HookParam(
                        MethodConst.TRIM_PHANTOM_PROCESSES_IF_NECESSARY,
                        param -> param.setResult(null),
                        null
                ),
                new HookParam(
                        MethodConst.KILL_PHANTOM_PROCESS_GROUP_LOCKED,
                        param -> param.setResult(null),
                        null,
                        XposedHelpers.findClass(ClassConst.PROCESS_RECORD1, loadPackageParam.classLoader),
                        XposedHelpers.findClass(ClassConst.PHANTOM_PROCESS_RECORD, loadPackageParam.classLoader),
                        int.class,
                        int.class,
                        String.class
                )
        };
    }
}
