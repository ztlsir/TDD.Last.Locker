package com.ztlsir.locker.fixture;

import com.ztlsir.locker.Locker;
import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;

public class LockerFixture {
    public static final String LOCKER_FULL_MSG = "Locker已满";
    public static final String FAKE_TICKET = "fake_ticket";
    public static final String ILLEGAL_TICKET_MSG = "非法票据";

    public static Locker createSSizeLocker(int capacity, int remain) {
        return createLocker(capacity, remain, BagSize.S);
    }

    public static Locker createMSizeLocker(int capacity, int remain) {
        return createLocker(capacity, remain, BagSize.M);
    }

    public static Locker createLSizeLocker(int capacity, int remain) {
        return createLocker(capacity, remain, BagSize.L);
    }

    private static Locker createLocker(int capacity, int remain, BagSize bagSize) {
        Locker locker = new Locker(capacity, bagSize);
        for (int i = 0; i < capacity - remain; i++) {
            locker.saveBag(new Bag(BagSize.S));
        }

        return locker;
    }
}
