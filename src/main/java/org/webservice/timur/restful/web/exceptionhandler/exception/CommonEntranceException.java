package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Общий предок используемых исключений.
 */
public abstract class CommonEntranceException extends RuntimeException {
    public CommonEntranceException(String message) {
        super(message);
    }
}
