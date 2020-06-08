package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Пользователь не найден в БД.
 */
public class UserNotFoundException extends CommonEntranceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
