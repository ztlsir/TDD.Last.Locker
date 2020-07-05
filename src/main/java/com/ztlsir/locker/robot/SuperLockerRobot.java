package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;

import java.util.List;

public class SuperLockerRobot {
    private static final String CONFIG_FAILED_MSG = "请配置L号Locker";
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.L;


    public SuperLockerRobot(List<Locker> lockers) {
        if (!this.isSupportLockers(lockers)) {
            throw new ConfigFailedException(CONFIG_FAILED_MSG);
        }
    }

    private boolean isSupportLockers(List<Locker> lockers) {
        return lockers.stream().anyMatch(this::isSupportLocker);
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == SUPPORT_BAG_SIZE;
    }
}
