package com.github.uissd.dontkill.hook.hooker.android;

import android.content.Context;
import android.os.Handler;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.FieldConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ActivityManagerConstantsHooker extends AndroidLogSupportHooker {

    public ActivityManagerConstantsHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.ACTIVITY_MANAGER_CONSTANTS, loadPackageParam);
    }

    @Override
    protected HookParam[] getConstructorsHookParams() {
        return new HookParam[]{
                new HookParam(
                        null,
                        this::setMCustomizedMaxCachedProcesses,
                        Context.class,
                        XposedHelpers.findClass(ClassConst.ACTIVITY_MANAGER_SERVICE1, loadPackageParam.classLoader),
                        Handler.class
                ),
        };
    }

    /**
     * mCustomizedMaxCachedProcesses常量决定着最大缓存进程数量默认值
     * 当缓存进程数量超过mCustomizedMaxCachedProcesses - 最大空进程数量 时
     * 系统会调用updateAndTrimProcessLSP函数kill掉部分缓存进程
     * mCustomizedMaxCachedProcesses原始默认值为32, 最大空进程数量默认值为16
     * hook使mCustomizedMaxCachedProcesses为Integer.MAX_VALUE
     * 即可避免缓存进程数量过多导致的杀后台事件发生
     * 注:
     * 最大空进程数量不建议修改, 即使空进程有另外的回收机制
     * 空进程是不包含任何活动组件的进程
     * 保留空进程的唯一用处是提高下次运行时的启动速度
     * 系统通常会终止空进程，以便将系统资源留给底层内核缓存
     * 此外, 不建议hook函数updateAndTrimProcessLSP
     * 此函数在不同版本有不同的逻辑, 且无法单独修改cachedProcessLimit变量
     * 源码链接:https://cs.android.com/android/platform/superproject/+/master:frameworks/base/services/core/java/com/android/server/am/ActivityManagerConstants.java;l=1065
     */
    private void setMCustomizedMaxCachedProcesses(MethodHookParam param) {
        XposedHelpers.setObjectField(param.thisObject, FieldConst.M_CUSTOMIZED_MAX_CACHED_PROCESSES, Integer.MAX_VALUE);
        logger.log(FieldConst.M_CUSTOMIZED_MAX_CACHED_PROCESSES + "=" + XposedHelpers.getObjectField(param.thisObject, FieldConst.M_CUSTOMIZED_MAX_CACHED_PROCESSES));
    }
}
