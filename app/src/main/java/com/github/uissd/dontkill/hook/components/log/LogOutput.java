package com.github.uissd.dontkill.hook.components.log;

import android.util.Log;

/**
 * 日志输出类, 用于测试日志类的堆栈信息输出格式
 */
public interface LogOutput {

    LogOutput DEFAULT_LOG_OUTPUT = new LogOutput() {
        @Override
        public void d(String tag, String msg) {
            Log.d(tag, msg);
        }

        @Override
        public void i(String tag, String msg) {
            Log.i(tag, msg);
        }

        @Override
        public void e(String tag, String msg) {
            Log.e(tag, msg);
        }
    };

    void d(String tag, String msg);

    void i(String tag, String msg);

    void e(String tag, String msg);
}

