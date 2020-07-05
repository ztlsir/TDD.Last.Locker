package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.LockerFullException;

import java.util.Comparator;
import java.util.List;

public class SuperLockerRobot extends BaseLockerRobot {
    private static final String CONFIG_FAILED_MSG = "请配置L号Locker";
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.L;
    private static final String BAG_SIZE_MISMATCHING_MSG = "仅支持包尺寸为L的票据";


    public SuperLockerRobot(List<Locker> lockers) {
        super(CONFIG_FAILED_MSG, SUPPORT_BAG_SIZE, BAG_SIZE_MISMATCHING_MSG, lockers);
    }

    @Override
    public Ticket saveBag(Bag bag) {
        return this.lockers.stream()
                .filter(locker -> !locker.isFull())
                .max(Comparator.comparing(Locker::getRemain))
                .orElseThrow(LockerFullException::new)
                .saveBag(bag);
    }
}
