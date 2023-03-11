package com.github.uissd.dontkill.hook.components.log;

import android.app.AlarmManager;
import android.app.AndroidAppHelper;
import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日志文件类, 实现定时创建/清理日志文件功能
 */
public class LogFile {

    private static final long ONE_MINUTE = 1000 * 60;
    private final String dirPath;
    private final String fileName;
    private final Logger logger;
    private volatile boolean opened = false;
    private volatile BufferedWriter mBufferedWriter;

    public LogFile(String tag, String dirPath, String fileName) {
        this.dirPath = dirPath;
        this.fileName = fileName;
        this.logger = new Logger(tag);
        updateFile();
        setUpdateFileTaskDelay();
    }

    private static long getTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        return tomorrow.getTimeInMillis();
    }

    /**
     * 打开文件一分钟后回写mBufferedWriter中的日志, 以便查看hook结果
     */
    private void openFile() {
        File mDir = new File(dirPath);
        File mFile = new File(dirPath, getFormatFileName(fileName, new Date()));
        synchronized (this) {
            try {
                if (mDir.mkdirs()) {
                    logger.d("mkdirs " + mDir);
                }
                if (mFile.createNewFile()) {
                    logger.d("create new file " + mFile);
                }
                if (mBufferedWriter != null) {
                    mBufferedWriter.close();
                }
                mBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mFile, false)));
                opened = true;
                flushDelay(mBufferedWriter);
            } catch (Exception e) {
                e.addSuppressed(new Throwable("open LogFile err: " + mFile));
                logger.e(e);
                opened = false;
            }
        }
    }

    private String getFormatFileName(String fileName, Date format) {
        String dateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA).format(format);
        return String.format("%s_%s.log", fileName, dateFormat);
    }

    public void write(String msg) throws IOException {
        if (opened) {
            synchronized (this) {
                mBufferedWriter.append(msg);
            }
        }
    }

    /**
     * 创建新的日志文件, 并删除两天前的日志文件
     */
    public void updateFile() {
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
                e.addSuppressed(new Throwable("updateFile err"));
                logger.e(e);
            }
        }
    }

    private void flushDelay(BufferedWriter bufferedWriter) {
        new Thread(() -> {
            try {
                Thread.sleep(ONE_MINUTE);
                synchronized (this) {
                    bufferedWriter.flush();
                }
            } catch (Exception e) {
                e.addSuppressed(new Throwable("flushDelay err"));
                logger.e(e);
            }
        }).start();
    }

    /**
     * 启动一分钟后获取Context启动定时任务
     */
    private void setUpdateFileTaskDelay() {
        new Thread(() -> {
            try {
                Thread.sleep(ONE_MINUTE);
                setAlarm(getTomorrow(), logger.tag, this::updateFileRepeat);
            } catch (Exception e) {
                e.addSuppressed(new Throwable("setUpdateFileTaskDelay err"));
                logger.e(e);
            }
        }).start();
    }

    /**
     * 每日00:00更新日志文件
     */
    private void updateFileRepeat() {
        try {
            updateFile();
            setAlarm(getTomorrow(), logger.tag, this::updateFileRepeat);
        } catch (Exception e) {
            e.addSuppressed(new Throwable("updateFileRepeat err"));
            logger.e(e);
        }
    }

    private void setAlarm(long triggerAtMillis, String tag, AlarmManager.OnAlarmListener listener) {
        Context context = AndroidAppHelper.currentApplication();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, tag, listener, null);
    }
}