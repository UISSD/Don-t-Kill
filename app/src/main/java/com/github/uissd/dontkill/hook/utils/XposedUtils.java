package com.github.uissd.dontkill.hook.utils;

import com.github.uissd.dontkill.hook.components.log.Logger;

import java.lang.reflect.Method;

import de.robv.android.xposed.XposedHelpers;

public class XposedUtils {

    public static Object staticField(Class<?> clazz, String field) {
        return XposedHelpers.getStaticObjectField(clazz, field);
    }

    public static Object field(Object thisObject, String field) {
        return XposedHelpers.getObjectField(thisObject, field);
    }

    public static Object callStatic(Class<?> clazz, String method, Class<?>[] paramTypes, Object... args) {
        return XposedHelpers.callStaticMethod(clazz, method, paramTypes, args);
    }

    public static Object call(Object thisObject, String method, Object... args) {
        return XposedHelpers.callMethod(thisObject, method, args);
    }

    public static Class<?> clazz(String className, ClassLoader classLoader) {
        return XposedHelpers.findClass(className, classLoader);
    }

    /**
     * 输出某个类的所有方法
     *
     * @param className   类名
     * @param classLoader 类加载器
     * @param tag         日志标签
     * @param logger      输日志出类
     */
    public static void logMethods(String className, ClassLoader classLoader, String tag, Logger logger) {
        Class<?> classProcessRecord = XposedHelpers.findClass(className, classLoader);
        for (Method method : classProcessRecord.getMethods()) {
            logger.d(tag, method.toGenericString(), logger.getFile());
        }
        for (Method method : classProcessRecord.getDeclaredMethods()) {
            logger.d(tag, method.toGenericString(), logger.getFile());
        }
    }
}
