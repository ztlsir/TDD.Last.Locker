package com.ztlsir.locker;

import com.ztlsir.locker.bag.BagSize;

public class Ticket {
    private final String serialNo;
    private final BagSize bagSize;

    public Ticket(String serialNo, BagSize bagSize) {
        this.serialNo = serialNo;
        this.bagSize = bagSize;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public BagSize getBagSize() {
        return this.bagSize;
    }
}