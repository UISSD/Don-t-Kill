package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import com.github.uissd.dontkill.hook.components.log.LogSupport;
import com.github.uissd.dontkill.hook.constants.PowerKeeper;
import com.github.uissd.dontkill.hook.constants.TagConst;

public abstract class PowerKeeperLogSupport extends LogSupport {

    public PowerKeeperLogSupport() {
        super(TagConst.ROOT, PowerKeeper.LOG_FILE, PowerKeeper.LOGGER);
    }
}
