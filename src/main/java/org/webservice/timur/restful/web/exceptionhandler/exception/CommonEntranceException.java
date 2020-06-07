package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Предок используемых исключений.
 */
public abstract class CommonEntranceException extends RuntimeException {
    public CommonEntranceException(String message) {
        super(message);
    }
}
