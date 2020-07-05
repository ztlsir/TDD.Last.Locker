package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.LockerFullException;

import java.util.List;

public class PrimaryLockerRobot extends BaseLockerRobot {
    private static final String CONFIG_FAILED_MSG = "请配置M号Locker";
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.M;
    private static final String BAG_SIZE_MISMATCHING_MSG = "仅支持包尺寸为M的票据";

    public PrimaryLockerRobot(List<Locker> lockers) {
        super(CONFIG_FAILED_MSG, SUPPORT_BAG_SIZE, BAG_SIZE_MISMATCHING_MSG, lockers);

    }

    public Ticket saveBag(Bag bag) {
        return this.lockers.stream()
                .filter(locker -> !locker.isFull())
                .findFirst()
                .orElseThrow(LockerFullException::new)
                .saveBag(bag);
    }
}
