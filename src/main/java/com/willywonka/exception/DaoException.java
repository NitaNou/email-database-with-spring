package com.willywonka.exception;

public class DaoException extends RuntimeException{

    public DaoException(String message, Exception cause) {
        super(message, cause);
    }


}
