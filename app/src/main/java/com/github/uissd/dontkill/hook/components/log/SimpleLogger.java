package com.github.uissd.dontkill.hook.components.log;

/**
 * 简单日志类, 解决log时频繁传入tag与logfile的烦恼
 */
public class SimpleLogger {

    private final Logger logger;
    private final String tag;
    private final LogFile file;

    public SimpleLogger(String tag, LogFile file, Logger logger) {
        this.logger = logger;
        this.tag = tag;
        this.file = file;
    }

    public void d(String msg) {
        logger.d(tag, msg, file);
    }

    public void d(String tag, String msg, LogFile file) {
        logger.d(tag, msg, file);
    }

    public void i(String msg) {
        logger.i(tag, msg, file);
    }

    public void i(String tag, String msg, LogFile file) {
        logger.i(tag, msg, file);
    }

    public void e(String msg) {
        logger.e(tag, msg, file);
    }

    public void e(String tag, String msg, LogFile file) {
        logger.e(tag, msg, file);
    }

    public void e(Exception e) {
        logger.e(tag, e, file);
    }

    public void e(String tag, Exception e, LogFile file) {
        logger.e(tag, e, file);
    }

    public void log(String msg) {
        logger.log(msg);
    }

    public void push(String msg) {
        logger.push(msg);
    }

    public void pop() {
        logger.pop(file);
    }

    public void pop(LogFile logFile) {
        logger.pop(logFile);
    }
}
