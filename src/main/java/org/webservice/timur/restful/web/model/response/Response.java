package org.webservice.timur.restful.web.model.response;

import lombok.Builder;
import lombok.Data;
import org.webservice.timur.restful.web.model.entity.Room;
import org.webservice.timur.restful.web.model.entity.User;

/**
 * Ответ нашего сервиса.
 */
@Data
@Builder
public class Response {

    /**
     * Данные пользователя.
     */
    private User user;

    /**
     * Данные комнаты.
     */
    private Room room;

    /**
     * Статус занятости комнаты.
     */
    private boolean occupied;

    /**
     * Числовое представление кода статуса запроса http.
     */
    private int httpStatus;

    /**
     * Строковое представление статуса запроса http с пояснением типа ответа.
     */
    private String httpStatusLabel;

    /**
     * Тест ответа/ошибки.
     */
    private String message;

    public Response() {
    }

    public Response(User user, Room room, boolean occupied, int httpStatus, String httpStatusLabel, String message) {
        this.user = user;
        this.room = room;
        this.occupied = occupied;
        this.httpStatus = httpStatus;
        this.httpStatusLabel = httpStatusLabel;
        this.message = message;
    }
}
