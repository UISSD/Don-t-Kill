package com.github.uissd.dontkill.hook.hooker.android;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.MethodConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class PhantomProcessListHooker extends AndroidLogSupportHooker {

    public PhantomProcessListHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.PHANTOM_PROCESS_LIST, loadPackageParam);
    }

    /**
     * 当phantom process数量超过32个时
     * trimPhantomProcessesIfNecessary函数会将部分进程杀死
     * hook使之置空, 即可防止因进程数量过多而导致的杀后台事件发生
     * 源码链接:https://cs.android.com/android/platform/superproject/+/android-12.0.0_r1:frameworks/base/services/core/java/com/android/server/am/PhantomProcessList.java;l=421
     */
    private static void trimNothing(MethodHookParam param) {
        param.setResult(null);
    }

    @Override
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{
                new HookParam(
                        MethodConst.TRIM_PHANTOM_PROCESSES_IF_NECESSARY,
                        PhantomProcessListHooker::trimNothing,
                        null
                ),
//                new HookParam(
//                        MethodConst.KILL_PHANTOM_PROCESS_GROUP_LOCKED,
//                        param -> param.setResult(null),
//                        null,
//                        XposedHelpers.findClass(ClassConst.PROCESS_RECORD1, loadPackageParam.classLoader),
//                        XposedHelpers.findClass(ClassConst.PHANTOM_PROCESS_RECORD, loadPackageParam.classLoader),
//                        int.class,
//                        int.class,
//                        String.class
//                )
        };
    }
}
