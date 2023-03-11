package com.github.uissd.dontkill.hook.components.log;

/**
 * 固定tag的支持缩进风格输出日志类, 继承于{@link Logger}类
 */
public class TaggedIndentSupportLogger extends Logger {

    private final IndentSupportLogger indentSupportLogger;

    public TaggedIndentSupportLogger(String tag, IndentSupportLogger indentSupportLogger) {
        super(tag, indentSupportLogger.file);
        this.indentSupportLogger = indentSupportLogger;
    }

    public void intent() {
        indentSupportLogger.intent();
    }

    public void unintent() {
        indentSupportLogger.unintent();
    }

    public void log(String msg) {
        indentSupportLogger.log(msg);
    }

    public void flush() {
        indentSupportLogger.flush();
    }

}
