package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;

public class PrimaryLockerRobot {
    private static final String CONFIG_FAILED_MSG = "请配置M号Locker";
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.M;

    public PrimaryLockerRobot(Locker locker) {
        if (!this.isSupportLocker(locker)) {
            throw new ConfigFailedException(CONFIG_FAILED_MSG);
        }
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == SUPPORT_BAG_SIZE;
    }
}
