package com.github.uissd.dontkill.hook.hooker;

public interface Hooker {

    /**
     * hook入口
     *
     * @return hook结果, true为hook成功, false为hook失败
     */
    boolean hook();
}
