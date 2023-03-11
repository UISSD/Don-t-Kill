package com.github.uissd.dontkill.hook.components.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持缩进风格输出的日志类
 * 支持多线程输出, 继承于{@link Logger}类
 */
public class IndentSupportLogger extends Logger {

    protected final Map<Thread, StringBuffer> bufferHashMap = new ConcurrentHashMap<>();
    protected final Map<Thread, Integer> intentHashMap = new ConcurrentHashMap<>();
    protected final StringBuilder intentUnit;

    public IndentSupportLogger(String tag, LogFile file, int spacesPerIntent) {
        super(tag, file);
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < spacesPerIntent; i++) {
            tmp.append(" ");
        }
        intentUnit = tmp;
    }

    public IndentSupportLogger(String tag, LogFile file) {
        this(tag, file, 2);
    }

    /**
     * 增加一级缩进
     * 需要结合{@link #log(String)}, {@link #unintent()}使用
     */
    public void intent() {
        Thread thread = Thread.currentThread();
        intentHashMap.put(thread, getIntent(thread) + 1);
    }

    /**
     * 减少一级缩进, 当缩进为空时输出日志并清空缓冲区
     * 需要结合{@link #log(String)}, {@link #intent()}使用
     */
    public void unintent() {
        Thread thread = Thread.currentThread();
        int intent = getIntent(thread) - 1;
        intentHashMap.put(thread, intent);
        if (intent <= 0) {
            flush();
            if (intent < 0) {
                e(new Exception("unintent too much"));
            }
        }
    }

    /**
     * 输出日志到缓冲区
     * 需结合{@link #intent()}, {@link #unintent()}使用
     */
    public void log(String msg) {
        Thread thread = Thread.currentThread();
        bufferHashMap.put(thread, getBuffer(thread).append(getNewLine(msg, getIntent(thread))));
    }

    /**
     * 输出日志并清空缓冲区
     */
    public void flush() {
        Thread thread = Thread.currentThread();
        d(getBuffer(thread).toString());
        bufferHashMap.remove(thread);
        intentHashMap.remove(thread);
    }

    private StringBuffer getBuffer(Thread thread) {
        return bufferHashMap.getOrDefault(thread, new StringBuffer());
    }

    private Integer getIntent(Thread thread) {
        return intentHashMap.getOrDefault(thread, 0);
    }

    private String getNewLine(String msg, int intent) {
        return "\n" + getIntentStr(intent) + msg;
    }

    private String getIntentStr(int intent) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < intent; i++) {
            indent.append(intentUnit);
        }
        return indent.toString();
    }
}
