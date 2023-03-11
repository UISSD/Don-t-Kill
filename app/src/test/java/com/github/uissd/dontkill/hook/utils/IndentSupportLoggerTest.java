package com.github.uissd.dontkill.hook.utils;

import com.github.uissd.dontkill.hook.components.log.IndentSupportLogger;
import com.github.uissd.dontkill.hook.components.log.LogFile;

import org.junit.Assert;
import org.junit.Test;

public class IndentSupportLoggerTest {

    @Test
    public void log() {
        IndentSupportLogger indentSupportLogger = new IndentSupportLogger("DontKill", null) {

            @Override
            public void d(String tag, String msg, LogFile file) {
                Assert.assertEquals("DontKill\n" +
                        "->进入第一个函数\n" +
                        "  第一个函数\n" +
                        "  ->进入第二个函数\n" +
                        "    第二个函数\n" +
                        "    第二个函数\n" +
                        "    ->进入第三个函数\n" +
                        "      第三个函数\n" +
                        "      第三个函数\n" +
                        "  第一个函数\n" +
                        "  ->进入第四个函数\n" +
                        "    第四个函数\n" +
                        "  第一个函数", tag + msg);
            }
        };
        indentSupportLogger.log("->进入第一个函数");
        indentSupportLogger.intent();
        indentSupportLogger.log("第一个函数");
        indentSupportLogger.log("->进入第二个函数");
        indentSupportLogger.intent();
        indentSupportLogger.log("第二个函数");
        indentSupportLogger.log("第二个函数");
        indentSupportLogger.log("->进入第三个函数");
        indentSupportLogger.intent();
        indentSupportLogger.log("第三个函数");
        indentSupportLogger.log("第三个函数");
        indentSupportLogger.unintent();
        indentSupportLogger.unintent();
        indentSupportLogger.log("第一个函数");
        indentSupportLogger.log("->进入第四个函数");
        indentSupportLogger.intent();
        indentSupportLogger.log("第四个函数");
        indentSupportLogger.unintent();
        indentSupportLogger.log("第一个函数");
        indentSupportLogger.unintent();
    }
}