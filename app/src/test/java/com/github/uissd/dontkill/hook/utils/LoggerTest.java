package com.github.uissd.dontkill.hook.utils;

import com.github.uissd.dontkill.hook.components.log.LogOutput;
import com.github.uissd.dontkill.hook.components.log.Logger;
import com.github.uissd.dontkill.hook.constants.TagConst;

import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {

    @Test
    public void log() {
        Logger logger = new Logger(TagConst.ROOT, new LogOutput() {

            @Override
            public void d(String tag, String msg) {
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

            @Override
            public void i(String tag, String msg) {
            }

            @Override
            public void e(String tag, String msg) {
            }
        });
        logger.push("->进入第一个函数");
        logger.log("第一个函数");
        logger.push("->进入第二个函数");
        logger.log("第二个函数");
        logger.log("第二个函数");
        logger.push("->进入第三个函数");
        logger.log("第三个函数");
        logger.log("第三个函数");
        logger.pop(null);
        logger.pop(null);
        logger.log("第一个函数");
        logger.push("->进入第四个函数");
        logger.log("第四个函数");
        logger.pop(null);
        logger.log("第一个函数");
        logger.pop(null);
    }
}