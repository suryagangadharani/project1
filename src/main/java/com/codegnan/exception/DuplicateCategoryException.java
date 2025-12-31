package com.codegnan.exception;

public class DuplicateCategoryException extends Exception {

    public DuplicateCategoryException() {
        // Default constructor
    }

    public DuplicateCategoryException(String message) {
        super(message);
    }

    public DuplicateCategoryException(Throwable cause) {
        super(cause);
    }

    public DuplicateCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCategoryException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
