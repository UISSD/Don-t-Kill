package com.github.uissd.dontkill.hook.constants;

public final class MethodConst {

    // com.github.uissd.dontkill.hook.hooker.android.ActivityManagerServiceHooker
    public static final String ON_TRANSACT = "onTransact";
    public static final String GET_CALLING_UID = "getCallingUid";
    public static final String GET_CALLING_PID = "getCallingPid";
    public static final String UPDATE_APP_PROCESS_CPU_TIME_LPR = "updateAppProcessCpuTimeLPr";
    public static final String CHECK_EXCESSIVE_POWER_USAGE_LPR = "checkExcessivePowerUsageLPr";

    // com.github.uissd.dontkill.hook.hooker.android.RecentTasksHooker
    public static final String TRIM_INACTIVE_RECENT_TASKS = "trimInactiveRecentTasks";
    public static final String NOTIFY_TASK_REMOVED = "notifyTaskRemoved";
    public static final String IS_IN_VISIBLE_RANGE = "isInVisibleRange";

    // com.github.uissd.dontkill.hook.hooker.android.PhantomProcessListHooker
    public static final String TRIM_PHANTOM_PROCESSES_IF_NECESSARY = "trimPhantomProcessesIfNecessary";
    public static final String KILL_PHANTOM_PROCESS_GROUP_LOCKED = "killPhantomProcessGroupLocked";

    // com.github.uissd.dontkill.hook.hooker.powerkeeper.PowerStateMachineHooker
    public static final String CLEAR_UNACTIVE_APPS = "clearUnactiveApps";
    public static final String CLEAR_APP_WHEN_SCREEN_OFF_TIME_OUT_IN_NIGHT = "clearAppWhenScreenOffTimeOutInNight";
    public static final String CLEAR_APP_WHEN_SCREEN_OFF_TIME_OUT = "clearAppWhenScreenOffTimeOut";

    // com.github.uissd.dontkill.hook.hooker.powerkeeper.SleepModeControllerNewHooker
    public static final String CLEAR_APP = "clearApp";

    // com.github.uissd.dontkill.hook.hooker.android.OomAdjusterHooker
    public static final String SHOULD_KILL_EXCESSIVE_PROCESSES = "shouldKillExcessiveProcesses";

    private MethodConst() {
    }

}
