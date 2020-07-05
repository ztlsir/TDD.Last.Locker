package com.ztlsir.locker.exception;

public class IllegalTicketException extends RuntimeException {

    private static final String ILLEGAL_TICKET_MSG = "非法票据";

    public IllegalTicketException() {
        super(ILLEGAL_TICKET_MSG);
    }
}
