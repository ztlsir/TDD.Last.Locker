package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.IllegalTicketException;
import com.ztlsir.locker.exception.LockerFullException;

import java.util.HashMap;

public class Locker {
    private static final String BAG_SIZE_MISMATCHING_MSG = "仅支持S号大小的票据";
    private final BagSize supportBagSize;
    private final int capacity;
    private final HashMap<Ticket, Bag> bags;

    public Locker(int capacity, BagSize supportBagSize) {
        this.capacity = capacity;
        this.supportBagSize = supportBagSize;

        this.bags = new HashMap<>();
    }

    public Ticket saveBag(Bag bag) {
        if (this.isFull()) {
            throw new LockerFullException();
        }

        Ticket ticket = new Ticket(Ticket.createId(), this.supportBagSize);
        this.bags.put(ticket, bag);

        return ticket;
    }

    public Bag takeBag(Ticket ticket) {
        if (!this.isMatchingBagSize(ticket)) {
            throw new IllegalTicketException(BAG_SIZE_MISMATCHING_MSG);
        }

        if (!this.contains(ticket)) {
            throw new IllegalTicketException();
        }

        return this.bags.remove(ticket);
    }

    private boolean isMatchingBagSize(Ticket ticket) {
        return ticket.getBagSize() == this.supportBagSize;
    }

    public boolean isFull() {
        return this.bags.size() >= this.getCapacity();
    }

    private int getCapacity() {
        return this.capacity;
    }

    public boolean contains(Ticket ticket) {
        return this.bags.containsKey(ticket);
    }

    public BagSize getSupportBagSize() {
        return this.supportBagSize;
    }

    public int getRemain() {
        return this.getCapacity() - this.bags.size();
    }
}
