package com.ztlsir.locker;

import com.ztlsir.locker.bag.BagSize;

import java.util.Objects;

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

    @Override
    public boolean equals(Object anTicket) {
        if (anTicket instanceof Ticket) {
            return this.equals((Ticket) anTicket);
        }

        return false;
    }

    private boolean equals(Ticket anTicket) {
        if (this == anTicket) {
            return true;
        }

        return Objects.equals(this.serialNo, anTicket.serialNo);

    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        int nullHashCode = 43;
        final Object $serialNo = this.getSerialNo();
        result = result * PRIME + ($serialNo == null ? nullHashCode : $serialNo.hashCode());
        final Object $bagSize = this.getBagSize();
        result = result * PRIME + ($bagSize == null ? nullHashCode : $bagSize.hashCode());
        return result;
    }
}