package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Нет свободной комнаты. Все занято
 */
public class NoEmptyRoomAvailableException extends CommonEntranceException {
    public NoEmptyRoomAvailableException(String message) {
        super(message);
    }
}
