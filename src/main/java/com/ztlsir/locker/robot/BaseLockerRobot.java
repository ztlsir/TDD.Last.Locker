package com.ztlsir.locker.robot;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.Ticket;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.ConfigFailedException;
import com.ztlsir.locker.exception.IllegalTicketException;

import java.util.List;

public abstract class BaseLockerRobot {
    private final BagSize supportBagSize;
    private final String bagSizeMismatchingMsg;
    List<Locker> lockers;

    BaseLockerRobot(
            String configFailedMsg,
            BagSize supportBagSize,
            String bagSizeMismatchingMsg,
            List<Locker> lockers) {
        this.supportBagSize = supportBagSize;
        this.bagSizeMismatchingMsg = bagSizeMismatchingMsg;

        if (!this.isSupportLockers(lockers)) {
            throw new ConfigFailedException(configFailedMsg);
        }

        this.lockers = lockers;
    }

    public abstract Ticket saveBag(Bag bag);

    public Bag takeBag(Ticket ticket) {
        if (!isMatchingBagSize(ticket)) {
            throw new IllegalTicketException(bagSizeMismatchingMsg);
        }

        return this.lockers.stream()
                .filter(locker -> locker.contains(ticket))
                .findAny()
                .orElseThrow(IllegalTicketException::new)
                .takeBag(ticket);
    }

    private boolean isMatchingBagSize(Ticket ticket) {
        return ticket.getBagSize() == supportBagSize;
    }

    private boolean isSupportLockers(List<Locker> lockers) {
        return lockers.stream().anyMatch(this::isSupportLocker);
    }

    private boolean isSupportLocker(Locker locker) {
        return locker.getSupportBagSize() == supportBagSize;
    }
}
