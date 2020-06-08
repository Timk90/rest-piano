package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Пользователь уже находится в другой комнате и не может войти в еще одну.
 */
public class UserAlreadyOccupiedAnotherRoomException extends CommonEntranceException {
    public UserAlreadyOccupiedAnotherRoomException(String message) {
        super(message);
    }
}
