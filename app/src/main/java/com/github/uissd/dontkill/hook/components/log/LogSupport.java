package com.github.uissd.dontkill.hook.components.log;

/**
 * 日志支持类, 默认日志输出tag为{根tag}.{实现类类名}
 */
public abstract class LogSupport {
    protected final String rootTag;
    protected final LogFile logFile;
    protected final String tag;
    protected final SimpleLogger logger;

    public LogSupport(String rootTag, LogFile logFile, Logger logger) {
        this.rootTag = rootTag;
        this.logFile = logFile;
        this.tag = String.join(".", rootTag, getClass().getSimpleName());
        this.logger = new SimpleLogger(tag, logFile, logger);
    }
}
