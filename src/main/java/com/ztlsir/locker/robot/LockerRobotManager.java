package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Storable;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;

import static com.ztlsir.locker.bag.BagSize.*;

public class LockerRobotManager {
    private static final BagSize SUPPORT_BAG_SIZE = S;
    private static final String CONFIG_FAILED_MSG = "请配置S号Locker";

    private final Locker locker;
    private final PrimaryLockerRobot primaryLockerRobot;
    private final SuperLockerRobot superLockerRobot;

    public LockerRobotManager(
            Locker locker,
            PrimaryLockerRobot primaryLockerRobot,
            SuperLockerRobot superLockerRobot) {
        if (!this.isSupportLocker(locker)) {
            throw new ConfigFailedException(CONFIG_FAILED_MSG);
        }

        this.locker = locker;
        this.primaryLockerRobot = primaryLockerRobot;
        this.superLockerRobot = superLockerRobot;
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == SUPPORT_BAG_SIZE;
    }

    public Ticket saveBag(Bag bag) {
        return getStorableByBagSize(bag.getBagSize()).saveBag(bag);

    }

    public Bag takeBag(Ticket ticket) {
        return getStorableByBagSize(ticket.getBagSize()).takeBag(ticket);
    }

    private Storable getStorableByBagSize(BagSize bagSize) {
        switch (bagSize) {
            case S:
                return this.locker;
            case M:
                return this.primaryLockerRobot;
            case L:
                return this.superLockerRobot;
        }

        return null;
    }
}
