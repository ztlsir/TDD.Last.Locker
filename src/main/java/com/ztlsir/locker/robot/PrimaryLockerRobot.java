package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;
import com.ztlsir.locker.exception.IllegalTicketException;
import com.ztlsir.locker.exception.LockerFullException;

import java.util.List;

public class PrimaryLockerRobot {
    private static final String CONFIG_FAILED_MSG = "请配置M号Locker";
    private static final BagSize SUPPORT_BAG_SIZE = BagSize.M;
    private static final String BAG_SIZE_MISMATCHING_MSG = "仅支持包尺寸为M的票据";
    private List<Locker> lockers;

    public PrimaryLockerRobot(List<Locker> lockers) {
        if (!this.isSupportLockers(lockers)) {
            throw new ConfigFailedException(CONFIG_FAILED_MSG);
        }

        this.lockers = lockers;
    }

    public Ticket saveBag(Bag bag) {
        return this.lockers.stream()
                .filter(locker -> !locker.isFull())
                .findFirst()
                .orElseThrow(LockerFullException::new)
                .saveBag(bag);
    }

    public Bag takeBag(Ticket ticket) {
        if (!this.isMatchingBagSize(ticket)) {
            throw new IllegalTicketException(BAG_SIZE_MISMATCHING_MSG);
        }

        return this.lockers.stream()
                .filter(locker -> locker.contains(ticket))
                .findFirst()
                .orElseThrow(IllegalTicketException::new)
                .takeBag(ticket);
    }

    private boolean isMatchingBagSize(Ticket ticket) {
        return ticket.getBagSize() == SUPPORT_BAG_SIZE;
    }

    private boolean isSupportLockers(List<Locker> lockers) {
        return lockers.stream().anyMatch(this::isSupportLocker);
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == SUPPORT_BAG_SIZE;
    }
}
