package com.github.uissd.dontkill.hook.components.log;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.robv.android.xposed.XposedBridge;

/**
 * 日志文件类, 实现定时创建/清理日志文件功能
 */
public class LogFile {

    private final String tag;
    private final String dirPath;
    private final String fileName;
    private volatile boolean opened = false;
    private volatile BufferedWriter mBufferedWriter;

    public LogFile(String tag, String dirPath, String fileName) {
        this.tag = tag;
        this.dirPath = dirPath;
        this.fileName = fileName;
        startUpdateTask();
    }

    /**
     * 创建文件一分钟后回写mBufferedWriter中的日志, 以便查看hook结果
     */
    private void startFlushTask() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (LogFile.this) {
                    try {
                        mBufferedWriter.flush();
                    } catch (IOException e) {
                        logErr("mBufferedWriter flush err", e);
                    }
                }
            }
        }, 1000 * 60);
    }

    /**
     * 创建定时任务, 每天12:00清理并创建日志文件
     */
    private void startUpdateTask() {
        LogFileUpdateTask updateTask = new LogFileUpdateTask();
        updateTask.run();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.HOUR, 12);
        tomorrow.set(Calendar.DATE, tomorrow.get(Calendar.DATE) + 1);
        new Timer().schedule(updateTask, tomorrow.getTime(), 1000 * 60 * 60 * 24);
    }

    private void openFile() {
        File mDir = new File(dirPath);
        File mFile = new File(dirPath, getFormatFileName(fileName, new Date()));
        synchronized (this) {
            try {
                if (mDir.mkdirs()) {
                    Log.d(tag, "mkdirs " + mDir);
                }
                if (mFile.createNewFile()) {
                    Log.d(tag, "create new file " + mFile);
                }
                if (mBufferedWriter != null) {
                    mBufferedWriter.close();
                }
                mBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFile, true)));
                opened = true;
                startFlushTask();
            } catch (Exception e) {
                logErr("open LogFile err: " + mFile, e);
                opened = false;
            }
        }
    }

    @NonNull
    private String getFormatFileName(String fileName, Date format) {
        String dateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA).format(format);
        return String.format("%s_%s.log", fileName, dateFormat);
    }

    private void logErr(String msg, Exception e) {
        String s = msg + "\n" + e + "\n" + Log.getStackTraceString(e);
        Log.e(tag, s);
        XposedBridge.log("[ERROR]" + tag + ": " + s);
    }

    public void write(String msg) {
        synchronized (this) {
            if (opened) {
                try {
                    mBufferedWriter.append(msg);
                } catch (Exception e) {
                    logErr("log write err", e);
                }
            }
        }
    }

    /**
     * 创建当天日志并清理前天日志, 即保留2天的日志文件
     */
    class LogFileUpdateTask extends TimerTask {
        @Override
        public void run() {
            openFile();
            if (opened) {
                try {
                    Calendar dayBeforeYesterday = Calendar.getInstance();
                    dayBeforeYesterday.set(Calendar.DATE, dayBeforeYesterday.get(Calendar.DATE) - 2);
                    File old = new File(dirPath, getFormatFileName(fileName, dayBeforeYesterday.getTime()));
                    if (old.exists() && !old.delete()) {
                        throw new Exception(old.getAbsolutePath() + " delete failed");
                    }
                } catch (Exception e) {
                    logErr("LogFileUpdateTask err", e);
                }
            }
        }
    }
}
