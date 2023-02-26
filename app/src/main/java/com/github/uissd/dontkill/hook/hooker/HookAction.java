package com.github.uissd.dontkill.hook.hooker;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public interface HookAction {

    /**
     * hook目标函数前/后所执行的行为
     *
     * @param param hook目标函数的实参
     */
    void execute(MethodHookParam param);
}
