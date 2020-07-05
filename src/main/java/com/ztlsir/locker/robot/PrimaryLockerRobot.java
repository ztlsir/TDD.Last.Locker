package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;

import java.util.List;

public class PrimaryLockerRobot {
    private static final String CONFIG_FAILED_MSG = "请配置M号Locker";
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.M;
    private List<Locker> lockers;

    public PrimaryLockerRobot(List<Locker> lockers) {
        if (!this.isSupportLockers(lockers)) {
            throw new ConfigFailedException(CONFIG_FAILED_MSG);
        }

        this.lockers = lockers;
    }

    public Ticket saveBag(Bag bag) {
        return this.lockers.get(0).saveBag(bag);
    }

    private boolean isSupportLockers(List<Locker> lockers) {
        return lockers.stream().anyMatch(this::isSupportLocker);
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == SUPPORT_BAG_SIZE;
    }
}
