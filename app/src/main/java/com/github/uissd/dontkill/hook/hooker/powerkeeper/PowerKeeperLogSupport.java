package com.github.uissd.dontkill.hook.hooker.powerkeeper;

import com.github.uissd.dontkill.hook.components.log.LogSupport;
import com.github.uissd.dontkill.hook.constants.PowerKeeper;

public abstract class PowerKeeperLogSupport extends LogSupport {

    public PowerKeeperLogSupport(String identity) {
        super(identity, PowerKeeper.INDENT_SUPPORT_LOGGER);
    }
}
