package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;
import com.ztlsir.locker.bag.BagSize;
import com.ztlsir.locker.exception.IllegalTicketException;
import com.ztlsir.locker.exception.LockerFullException;

import java.util.HashMap;

public class Locker {
    private final BagSize bagSize;
    private final int capacity;
    private final HashMap<Ticket, Bag> bags;

    public Locker(int capacity, BagSize bagSize) {
        this.capacity = capacity;
        this.bagSize = bagSize;

        this.bags = new HashMap<>();
    }

    public Ticket saveBag(Bag bag) {
        if (this.isFull()) {
            throw new LockerFullException();
        }

        Ticket ticket = new Ticket(Ticket.createId(), this.bagSize);
        this.bags.put(ticket, bag);

        return ticket;
    }

    private boolean isFull() {
        return this.bags.size() >= this.getCapacity();
    }

    private int getCapacity() {
        return this.capacity;
    }

    public Bag takeBag(Ticket ticket) {
        if (!this.bags.containsKey(ticket)) {
            throw new IllegalTicketException();
        }

        return this.bags.get(ticket);
    }
}
