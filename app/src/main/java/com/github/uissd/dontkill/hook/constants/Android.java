package com.github.uissd.dontkill.hook.constants;

import com.github.uissd.dontkill.BuildConfig;
import com.github.uissd.dontkill.hook.components.log.IndentSupportLogger;
import com.github.uissd.dontkill.hook.components.log.LogFile;

public final class Android {
    /**
     * 非DEBUG模式不输出日志文件
     */
    public static final LogFile LOG_FILE = BuildConfig.DEBUG ? new LogFile(TagConst.LOG_FILE, "/cache/uissd/dontkill", "DontKill") : null;
    public static final IndentSupportLogger INDENT_SUPPORT_LOGGER = new IndentSupportLogger(TagConst.ROOT, LOG_FILE);

    private Android() {
    }
}
