package com.github.uissd.dontkill.hook.components.log;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import de.robv.android.xposed.XposedBridge;

/**
 * 日志类, 实现代码缩进风格的堆栈信息输出, 也可进行常规的info, debug, error输出
 */
public class Logger {

    private final String rootTag;
    private final LogOutput output;
    private volatile String buffer = "";
    private volatile int stack;

    public Logger() {
        this("", LogOutput.DEFAULT_LOG_OUTPUT);
    }

    public Logger(String rootTag) {
        this(rootTag, LogOutput.DEFAULT_LOG_OUTPUT);
    }

    public Logger(LogOutput output) {
        this("", output);
    }

    public Logger(String rootTag, LogOutput output) {
        this.rootTag = rootTag;
        this.output = output;
    }

    /**
     * hook的目标函数执行前需调用一次push, tag用于记录当前堆栈信息
     * 一般用于记录当前调用函数, 需要结合pop(LogFile file)使用
     *
     * @param msg 当前堆栈信息
     */
    public void push(String msg) {
        synchronized (this) {
            buffer += getNewLine(msg);
            stack++;
        }
    }

    /**
     * hook的目标函数执行后需调用一次pop, file用来决定输出的日志文件
     * 只有堆栈清空时才会输出, 需要结合push(String msg)使用
     * file为空时不输出日志文件
     *
     * @param file 日志文件
     */
    public void pop(LogFile file) {
        synchronized (this) {
            if (stack > 0) {
                stack--;
            } else {
                logErr("pop too much", new Exception());
            }
            if (stack == 0) {
                d(rootTag, buffer, file);
                buffer = "";
            }
        }
    }

    /**
     * hook的目标函数调用期间使用的日志输出方法
     * 可实现代码缩进风格的堆栈信息输出, 默认为debug级别
     * log的内容不会立即输出, 只有堆栈清空时才会输出
     *
     * @param msg 需要输出的日志信息
     */
    public void log(String msg) {
        synchronized (this) {
            buffer += getNewLine(msg);
        }
    }

    /**
     * 输出并清空日志缓冲区, 重置堆栈
     *
     * @param file 日志文件
     */
    public void flush(LogFile file) {
        stack = 0;
        pop(file);
    }

    public void d(String tag, String msg, LogFile file) {
        output.d(tag, msg);
        saveLog("[DEBUG]", tag, msg, file);
    }


    public void i(String tag, String msg, LogFile file) {
        output.i(tag, msg);
        saveLog("[INFO]", tag, msg, file);
    }

    public void e(String tag, String msg, LogFile file) {
        XposedBridge.log("[ERROR]" + tag + ": " + msg);
        output.e(tag, msg);
        saveLog("[ERROR]", tag, msg, file);
    }

    public void e(String tag, Exception e, LogFile file) {
        String stackTrace = Log.getStackTraceString(e == null ? new Throwable() : e);
        String msg = e + "\n" + stackTrace;
        XposedBridge.log("[ERROR]" + tag + ": " + msg);
        output.e(tag, msg);
        saveLog("[ERROR]", tag, msg, file);
    }

    private String getFormatMsg(String level, String tag, String msg) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(System.currentTimeMillis()) + level + tag + ": " + msg + "\n";
    }

    private void saveLog(String level, String tag, String msg, LogFile file) {
        if (file != null) {
            try {
                file.write(getFormatMsg(level, tag, msg));
            } catch (IOException e) {
                logErr("write err", e);
            }
        }
    }

    private void logErr(String msg, Exception e) {
        String s = msg + "\n" + e + "\n" + Log.getStackTraceString(e);
        e(String.join(".", rootTag, getClass().getSimpleName()), s, null);
    }

    private String getNewLine(String msg) {
        return "\n" + getIntent() + msg;
    }

    private String getIntent() {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < stack; i++) {
            indent.append("  ");
        }
        return indent.toString();
    }
}
