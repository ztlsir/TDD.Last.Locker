package com.ztlsir.locker.bag;

public class Bag {
    private final BagSize bagSize;

    public Bag(BagSize bagSize) {
        this.bagSize = bagSize;
    }

    public BagSize getBagSize() {
        return this.bagSize;
    }
}
