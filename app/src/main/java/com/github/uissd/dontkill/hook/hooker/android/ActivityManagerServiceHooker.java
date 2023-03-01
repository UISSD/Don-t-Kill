package com.github.uissd.dontkill.hook.hooker.android;

import static com.github.uissd.dontkill.hook.utils.XposedUtils.call;

import android.os.Parcel;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.MethodConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ActivityManagerServiceHooker extends AndroidLogSupportHooker {

    public ActivityManagerServiceHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.ACTIVITY_MANAGER_SERVICE, loadPackageParam);
    }

    /**
     * checkExcessivePowerUsageLPr函数检查后台进程是否过量使用CPU时间
     * 当进程判断为过量使用后, 系统会将其kill掉
     * hook使之返回false, 即可避免因后台进程因过量使用CPU时间而kill掉
     * 源码链接:https://cs.android.com/android/platform/superproject/+/master:frameworks/base/services/core/java/com/android/server/am/ActivityManagerService.java;l=15343
     */
    private static void noExcessivePowerUsage(MethodHookParam param) {
        param.setResult(false);
    }

    @Override
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{
                new HookParam(
                        MethodConst.CHECK_EXCESSIVE_POWER_USAGE_LPR,
                        null,
                        ActivityManagerServiceHooker::noExcessivePowerUsage,
                        long.class,
                        boolean.class,
                        long.class,
                        String.class,
                        String.class,
                        int.class,
                        XposedHelpers.findClass(ClassConst.PROCESS_RECORD, loadPackageParam.classLoader)
                ),
//                调试用
//                new HookParam(
//                        MethodConst.UPDATE_APP_PROCESS_CPU_TIME_LPR,
//                        this::setDoCpuKillsFalse,
//                        null,
//                        long.class,
//                        boolean.class,
//                        long.class,
//                        int.class,
//                        XposedHelpers.findClass(ClassConst.PROCESS_RECORD, loadPackageParam.classLoader)
//                ),
//                new HookParam(
//                        MethodConst.ON_TRANSACT,
//                        this::onTransact_B,
//                        null,
//                        int.class,
//                        Parcel.class,
//                        Parcel.class,
//                        int.class
//                ),
        };
    }

    private void setDoCpuKillsFalse(MethodHookParam param) {
        if ((boolean) param.args[1]) {
            param.args[1] = false;
            logger.log("set doCpuKills false");
        }
    }

    private void onTransact_B(MethodHookParam param) {
        int code = (int) param.args[0];
        Parcel data = (Parcel) param.args[1];
        Parcel reply = (Parcel) param.args[2];
        int flags = (int) param.args[3];
        int uid = (int) call(param.thisObject, MethodConst.GET_CALLING_UID);
        int pid = (int) call(param.thisObject, MethodConst.GET_CALLING_PID);
        logger.log("calling uid=" + uid + ", calling pid=" + pid +
                ", code=" + code + ", flags=" + flags +
                ", data=" + data + ", reply=" + reply);
    }
}
