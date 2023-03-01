package com.github.uissd.dontkill.hook.hooker.android;

import static com.github.uissd.dontkill.hook.utils.XposedUtils.field;

import androidx.annotation.Nullable;

import com.github.uissd.dontkill.hook.constants.ClassConst;
import com.github.uissd.dontkill.hook.constants.FieldConst;
import com.github.uissd.dontkill.hook.constants.MethodConst;
import com.github.uissd.dontkill.hook.hooker.HookParam;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class RecentTasksHooker extends AndroidLogSupportHooker {

    public RecentTasksHooker(LoadPackageParam loadPackageParam) {
        super(ClassConst.RECENT_TASKS, loadPackageParam);
    }

    /**
     * isInVisibleRange函数会返回一个最近任务是否可见
     * 任务可见性由可见范围和非活动时间决定, 可见范围优先于非活动时间判定
     * 当超过可见范围或非活动时间超出阈值时, 部分任务将会变得不可见且可能会被系统kill掉
     * hook使当前可见任务数量为0, 将不会因最近任务可见范围和非活动时间而触发系统杀后台事件
     * 源码链接:https://cs.android.com/android/platform/superproject/+/android-12.0.0_r1:frameworks/base/services/core/java/com/android/server/wm/RecentTasks.java;l=1399
     */
    private static void alwaysInRange(MethodHookParam param) {
        param.args[2] = 0;
    }

    @Override
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{
                new HookParam(
                        MethodConst.IS_IN_VISIBLE_RANGE,
                        RecentTasksHooker::alwaysInRange,
                        null,
                        XposedHelpers.findClass(ClassConst.TASK, loadPackageParam.classLoader),
                        int.class,
                        int.class,
                        boolean.class
                ),
//                new HookParam(
//                        MethodConst.TRIM_INACTIVE_RECENT_TASKS,
//                        param -> param.setResult(null),
//                        null
//                ),
//                new HookParam(
//                        MethodConst.NOTIFY_TASK_REMOVED,
//                        this::B_notifyTaskRemoved,
//                        null,
//                        XposedHelpers.findClass(ClassConst.TASK, loadPackageParam.classLoader),
//                        boolean.class,
//                        boolean.class
//                )
        };
    }

    private void B_notifyTaskRemoved(MethodHookParam param) {
        Object task = param.args[0];
        boolean wasTrimmed = (boolean) param.args[1];
        boolean killProcess = (boolean) param.args[2];
        List<Object> mTasks = (List<Object>) field(param.thisObject, FieldConst.M_TASKS);
        for (Object mTask : mTasks) {
            logger.log("task=" + mTask);
        }
        if (killProcess) {
            logger.log("kill process, task=" + task + ", wasTrimmed=" + wasTrimmed);
        }
        logger.log("killed task=" + task);
        logger.log(android.util.Log.getStackTraceString(new Throwable()));
    }

    @Nullable
    private Object trimNothing() {
        logger.log("do nothing");
        return null;
    }
}
