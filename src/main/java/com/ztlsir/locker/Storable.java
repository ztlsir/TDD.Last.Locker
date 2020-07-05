package com.ztlsir.locker;

import com.ztlsir.locker.bag.Bag;

public interface Storable {
    Ticket saveBag(Bag bag);

    Bag takeBag(Ticket ticket);
}
