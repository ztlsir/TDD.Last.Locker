package com.ztlsir.locker.exception;

public class LockerFullException extends RuntimeException {

    private static final String LOCKER_FULL_MSG = "Locker已满";

    public LockerFullException() {
        super(LOCKER_FULL_MSG);
    }
}
