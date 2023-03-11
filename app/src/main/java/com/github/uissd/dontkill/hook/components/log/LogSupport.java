package com.github.uissd.dontkill.hook.components.log;

/**
 * 日志支持类
 */
public abstract class LogSupport {

    protected final String tag;
    protected final TaggedIndentSupportLogger logger;

    /**
     * @param suffix              {@link #tag}后缀
     * @param indentSupportLogger 默认log通道, 并传入{@link #tag}前缀
     */
    public LogSupport(String suffix, IndentSupportLogger indentSupportLogger) {
        this.tag = String.join(".", indentSupportLogger.tag, suffix);
        this.logger = new TaggedIndentSupportLogger(tag, indentSupportLogger);
    }
}
