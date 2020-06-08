package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Комната уже занята другим пользователем.
 */
public class RoomHasAnotherOccupationStateException extends CommonEntranceException {
    public RoomHasAnotherOccupationStateException(String message) {
        super(message);
    }
}
