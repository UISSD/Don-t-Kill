package com.github.uissd.dontkill.hook.components.log;

import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import de.robv.android.xposed.XposedBridge;

/**
 * 日志类, 支持常规的info, debug, error输出
 */
public class Logger {

    protected final String tag;
    protected final LogFile file;

    public Logger() {
        this("", null);
    }

    public Logger(String tag) {
        this(tag, null);
    }

    public Logger(LogFile file) {
        this("", file);
    }

    public Logger(String tag, LogFile file) {
        this.tag = tag;
        this.file = file;
    }

    public Logger(Logger logger) {
        this(logger.tag, logger.file);
    }

    public void d(String msg) {
        d(tag, msg, file);
    }

    public void i(String msg) {
        i(tag, msg, file);
    }

    public void e(String msg) {
        e(tag, msg, file);
    }

    public void e(Exception e) {
        e(tag, e, file);
    }

    public void d(String tag, String msg, LogFile file) {
        Log.d(tag, msg);
        saveLog("[DEBUG]", tag, msg, file);
    }

    public void i(String tag, String msg, LogFile file) {
        Log.i(tag, msg);
        saveLog("[INFO]", tag, msg, file);
    }

    public void e(String tag, String msg, LogFile file) {
        XposedBridge.log("[ERROR]" + tag + ": " + msg);
        Log.e(tag, msg);
        saveLog("[ERROR]", tag, msg, file);
    }

    public void e(String tag, Exception e, LogFile file) {
        String msg = e + "\n" + Log.getStackTraceString(e == null ? new Throwable() : e);
        XposedBridge.log("[ERROR]" + tag + ": " + msg);
        Log.e(tag, msg);
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
                e.addSuppressed(new Throwable("write err"));
                e(e);
            }
        }
    }

    public String getTag() {
        return tag;
    }

    public LogFile getFile() {
        return file;
    }
}

