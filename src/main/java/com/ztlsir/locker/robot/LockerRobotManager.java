package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;

public class LockerRobotManager {
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.S;
    private static final String CONFIG_FAILED_MSG = "请配置S号Locker";

    public LockerRobotManager(Locker locker, PrimaryLockerRobot primaryLockerRobot, SuperLockerRobot superLockerRobot) {
        if (!this.isSupportLocker(locker)) {
            throw new ConfigFailedException(CONFIG_FAILED_MSG);
        }
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == SUPPORT_BAG_SIZE;
    }
}
