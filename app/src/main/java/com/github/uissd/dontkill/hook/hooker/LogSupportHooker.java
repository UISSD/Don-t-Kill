package com.github.uissd.dontkill.hook.hooker;

import com.github.uissd.dontkill.hook.components.log.LogSupport;
import com.github.uissd.dontkill.hook.components.log.IndentSupportLogger;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * 支持日志输出的Hooker类
 */
public abstract class LogSupportHooker extends LogSupport implements Hooker {

    protected final String hookTag;
    protected final String targetClass;
    protected final LoadPackageParam loadPackageParam;

    public LogSupportHooker(String targetClass, IndentSupportLogger indentSupportLogger, LoadPackageParam loadPackageParam) {
        super(targetClass, indentSupportLogger);
        this.targetClass = targetClass;
        this.loadPackageParam = loadPackageParam;
        this.hookTag = String.join(".", indentSupportLogger.getTag(), "Hook");
    }

    public boolean hook() {
        try {
            hookConstructors();
            hookMethods();
            return true;
        } catch (Exception e) {
            logger.e(hookTag, e, logger.getFile());
            return false;
        }
    }

    /**
     * hook的目标函数数组, 默认不hook任何函数
     */
    protected HookParam[] getMethodHookParams() {
        return new HookParam[]{};
    }

    /**
     * hook的目标构造函数数组, 默认不hook任何构造函数
     */
    protected HookParam[] getConstructorsHookParams() {
        return new HookParam[]{};
    }

    protected String getSimpleClassName() {
        return getClass().getSimpleName();
    }

    private void hookConstructors() {
        for (HookParam hookParam : getConstructorsHookParams()) {
            List<Object> args = getArgs(hookParam);
            XposedHelpers.findAndHookConstructor(
                    targetClass,
                    loadPackageParam.classLoader,
                    args.toArray()
            );
        }
        logger.i(hookTag, "hook " + getSimpleClassName() + " constructors success", logger.getFile());
    }

    private void hookMethods() {
        for (HookParam hookParam : getMethodHookParams()) {
            List<Object> args = getArgs(hookParam);
            XposedHelpers.findAndHookMethod(
                    targetClass,
                    loadPackageParam.classLoader,
                    hookParam.getMethodName(),
                    args.toArray()
            );
        }
        logger.i(hookTag, "hook " + getSimpleClassName() + " methods success", logger.getFile());
    }

    private List<Object> getArgs(HookParam hookParam) {
        List<Object> args = hookParam.getArgs();
        args.add(new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                logMethodInfo(param);
                try {
                    if (hookParam.getBeforeMethod() != null) {
                        hookParam.getBeforeMethod().execute(param);
                    }
                } catch (Exception e) {
                    logger.e(e);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                    if (hookParam.getAfterMethod() != null) {
                        hookParam.getAfterMethod().execute(param);
                    }
                } catch (Exception e) {
                    logger.e(e);
                }
                logMethodInfoEnd();
            }
        });
        return args;
    }

    /**
     * 输出所调用函数的名字与参数
     *
     * @param param hook的方法参数
     */
    private void logMethodInfo(MethodHookParam param) {
        List<String> argValues = new ArrayList<>();
        for (Object arg : param.args) {
            argValues.add(arg == null ? null : arg.toString());
        }
        String params = String.join(",", argValues);
        logger.log("->" + param.method.getName() + "(" + params + ") " + param.method.getDeclaringClass());
        logger.intent();
    }

    private void logMethodInfoEnd() {
        logger.unintent();
    }

}
