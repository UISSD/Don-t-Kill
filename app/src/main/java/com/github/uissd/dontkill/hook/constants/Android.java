package com.github.uissd.dontkill.hook.constants;

import com.github.uissd.dontkill.BuildConfig;
import com.github.uissd.dontkill.hook.components.log.LogFile;
import com.github.uissd.dontkill.hook.components.log.LogOutput;
import com.github.uissd.dontkill.hook.components.log.Logger;

public final class Android {
    /**
     * 非DEBUG模式不输出日志文件
     */
    public static final LogFile LOG_FILE = BuildConfig.DEBUG ? new LogFile(TagConst.LOG_FILE, "/cache/uissd/dontkill", "DontKill") : null;
    public static final Logger LOGGER = new Logger(TagConst.ROOT, LogOutput.DEFAULT_LOG_OUTPUT);

    private Android() {
    }
}
