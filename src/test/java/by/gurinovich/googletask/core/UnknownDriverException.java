package by.gurinovich.googletask.core;

public class UnknownDriverException extends Exception {

    public UnknownDriverException() {
    }

    public UnknownDriverException(String message) {
        super(message);
    }

    public UnknownDriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDriverException(Throwable cause) {
        super(cause);
    }

    public UnknownDriverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
