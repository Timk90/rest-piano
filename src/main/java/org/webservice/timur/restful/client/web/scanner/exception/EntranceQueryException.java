package org.webservice.timur.restful.client.web.scanner.exception;

/**
 * Ошибка сканера при парсинге запросов на сервер.
 */
public class EntranceQueryException extends RuntimeException {
    public EntranceQueryException(String message) {
        super(message);
    }
}
