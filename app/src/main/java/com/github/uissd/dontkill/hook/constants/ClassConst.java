package com.github.uissd.dontkill.hook.constants;

public final class ClassConst {

    // com.github.uissd.dontkill.hook.hooker.android.ActivityManagerServiceHooker
    public static final String ACTIVITY_MANAGER_SERVICE = "com.android.server.am.ActivityManagerService";
    public static final String PROCESS_RECORD = "com.android.server.am.ProcessRecord";

    // com.github.uissd.dontkill.hook.hooker.android.RecentTasksHooker
    public static final String RECENT_TASKS = "com.android.server.wm.RecentTasks";
    public static final String TASK = "com.android.server.wm.Task";

    // com.github.uissd.dontkill.hook.hooker.android.PhantomProcessListHooker
    public static final String PHANTOM_PROCESS_LIST = "com.android.server.am.PhantomProcessList";
    public static final String PROCESS_RECORD1 = "com.android.server.am.ProcessRecord";
    public static final String PHANTOM_PROCESS_RECORD = "com.android.server.am.PhantomProcessRecord";

    // com.github.uissd.dontkill.hook.hooker.powerkeeper.PowerStateMachineHooker
    public static final String POWER_STATE_MACHINE = "com.miui.powerkeeper.statemachine.PowerStateMachine";

    // com.github.uissd.dontkill.hook.hooker.powerkeeper.SleepModeControllerNewHooker
    public static final String SLEEP_MODE_CONTROLLER_NEW = "com.miui.powerkeeper.statemachine.SleepModeControllerNew";

    // com.github.uissd.dontkill.hook.hooker.android.OomAdjusterHooker
    public static final String OOM_ADJUSTER = "com.android.server.am.OomAdjuster";

    // com.github.uissd.dontkill.hook.hooker.android.ActivityManagerConstantsHooker
    public static final String ACTIVITY_MANAGER_CONSTANTS = "com.android.server.am.ActivityManagerConstants";
    public static final String ACTIVITY_MANAGER_SERVICE1 = "com.android.server.am.ActivityManagerService";

    private ClassConst() {
    }

}
