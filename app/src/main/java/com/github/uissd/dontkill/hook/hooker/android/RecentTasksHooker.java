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
     * 任务可见性由可见范围和非活动时间决定, 当超过可见范围或非活动时间超出阈值时
     * 部分任务将会变得不可见, 而不可见任务可能会被系统kill掉
     * hook使当前可见任务数量为0, 将不会因最近任务可见范围和非活动时间而触发系统杀后台事件
     * <p>
     * isInVisibleRange源码:
     * <p>
     * // @return whether the given visible task is within the policy range.
     * private boolean isInVisibleRange(Task task, int taskIndex, int numVisibleTasks,
     * boolean skipExcludedCheck) {
     * if (!skipExcludedCheck) {
     * // Keep the most recent task of home display even if it is excluded from recents.
     * final boolean isExcludeFromRecents =
     * (task.getBaseIntent().getFlags() & FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
     * == FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
     * if (isExcludeFromRecents) {
     * if (DEBUG_RECENTS_TRIM_TASKS) {
     * Slog.d(TAG,
     * "\texcludeFromRecents=true, taskIndex = " + taskIndex
     * + ", isOnHomeDisplay: " + task.isOnHomeDisplay());
     * }
     * // The Recents is only supported on default display now, we should only keep the
     * // most recent task of home display.
     * return (task.isOnHomeDisplay() && taskIndex == 0);
     * }
     * }
     * <p>
     * if (mMinNumVisibleTasks >= 0 && numVisibleTasks <= mMinNumVisibleTasks) {
     * // Always keep up to the min number of recent tasks, after that fall through to the
     * // checks below
     * return true;
     * }
     * <p>
     * // The given task if always treated as in visible range if it is the origin of pinned task.
     * if (task.mChildPipActivity != null) return true;
     * <p>
     * if (mMaxNumVisibleTasks >= 0) {
     * // Always keep up to the max number of recent tasks, but return false afterwards
     * return numVisibleTasks <= mMaxNumVisibleTasks;
     * }
     * <p>
     * if (mActiveTasksSessionDurationMs > 0) {
     * // Keep the task if the inactive time is within the session window, this check must come
     * // after the checks for the min/max visible task range
     * if (task.getInactiveDuration() <= mActiveTasksSessionDurationMs) {
     * return true;
     * }
     * }
     * <p>
     * return false;
     * }
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
