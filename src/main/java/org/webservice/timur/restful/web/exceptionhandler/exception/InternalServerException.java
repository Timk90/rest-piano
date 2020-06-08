package org.webservice.timur.restful.web.exceptionhandler.exception;

/**
 * Ошибка на стороне сервера.
 * На самом деле используется, если пользователь или комната не найдены в БД
 */
public class InternalServerException extends CommonEntranceException {
    public InternalServerException(String message) {
        super(message);
    }
}
