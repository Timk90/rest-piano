package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Пользователь не найден в указанной комнате. При запросе на проверку возможности выхода из комнаты.
 */
public class UserNotFoundInRoomException extends CommonEntranceException {
    public UserNotFoundInRoomException(String message) {
        super(message);
    }
}
