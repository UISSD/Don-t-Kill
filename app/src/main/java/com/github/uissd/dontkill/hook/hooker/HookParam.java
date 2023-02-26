package com.github.uissd.dontkill.hook.hooker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HookParam {

    private final String methodName;
    private final HookAction beforeMethod;
    private final HookAction afterMethod;
    private final List<Object> args;

    /**
     * hook构造函数所需的参数
     *
     * @param beforeMethod 目标构造函数执行前行为
     * @param afterMethod  目标构造函数执行后行为
     * @param args         目标构造函数参数
     */
    public HookParam(HookAction beforeMethod, HookAction afterMethod, Object... args) {
        this(null, beforeMethod, afterMethod, args);
    }

    /**
     * hook函数所需的参数
     *
     * @param methodName   目标函数名
     * @param beforeMethod 目标函数执行前行为
     * @param afterMethod  目标函数执行后行为
     * @param args         目标函数参数
     */
    public HookParam(String methodName, HookAction beforeMethod, HookAction afterMethod, Object... args) {
        this.methodName = methodName;
        this.beforeMethod = beforeMethod;
        this.afterMethod = afterMethod;
        this.args = new ArrayList<>(Arrays.asList(args));
    }

    public String getMethodName() {
        return methodName;
    }

    public List<Object> getArgs() {
        return args;
    }

    public HookAction getBeforeMethod() {
        return beforeMethod;
    }

    public HookAction getAfterMethod() {
        return afterMethod;
    }
}
