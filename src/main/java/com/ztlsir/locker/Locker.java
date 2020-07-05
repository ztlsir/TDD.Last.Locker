package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;

public class Locker {
    private static final String SERIAL_NO = "1";

    private final BagSize bagSize;
    private Bag bag;

    public Locker(int capacity, BagSize bagSize) {
        this.bagSize = bagSize;
    }

    public Ticket saveBag(Bag bag) {
        this.bag = bag;
        return new Ticket(SERIAL_NO, this.bagSize);
    }

    public Bag takeBag(Ticket ticket) {
        return this.bag;
    }
}
