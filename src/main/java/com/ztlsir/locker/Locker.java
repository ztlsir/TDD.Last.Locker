package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;

public class Locker {
    public Locker(int capacity, BagSize bagSize) {
    }

    public Ticket saveBag(Bag bag) {
        return new Ticket();
    }
}
