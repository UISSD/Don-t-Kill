package com.github.uissd.dontkill.hook.utils;

import com.github.uissd.dontkill.hook.components.log.LogOutput;

import java.lang.reflect.Method;

import de.robv.android.xposed.XposedHelpers;

public class XposedUtils {

    public static Object field(Object thisObject, String field) {
        return XposedHelpers.getObjectField(thisObject, field);
    }

    public static Object call(Object thisObject, String method, Object... args) {
        return XposedHelpers.callMethod(thisObject, method, args);
    }

    /**
     * 输出某个类的所有方法
     *
     * @param className   类名
     * @param classLoader 类加载器
     * @param tag         日志标签
     * @param output      输日志出类
     */
    public static void logMethods(String className, ClassLoader classLoader, String tag, LogOutput output) {
        Class<?> classProcessRecord = XposedHelpers.findClass(className, classLoader);
        for (Method method : classProcessRecord.getMethods()) {
            output.d(tag, method.toGenericString());
        }
        for (Method method : classProcessRecord.getDeclaredMethods()) {
            output.d(tag, method.toGenericString());
        }
    }
}
