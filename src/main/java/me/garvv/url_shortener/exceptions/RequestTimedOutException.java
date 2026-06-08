package me.garvv.url_shortener.exceptions;

public class RequestTimedOutException extends RuntimeException {
    public RequestTimedOutException(String msg) {
        super(msg);
    }
}
