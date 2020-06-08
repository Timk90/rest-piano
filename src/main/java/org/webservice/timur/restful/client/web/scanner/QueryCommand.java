package org.webservice.timur.restful.client.web.scanner;

/**
 * Функциональный интерфейс необходимый для враппера эксепшинов сканера.
 * @param <T> параметр
 */
public interface QueryCommand<T> {
    T get() throws Exception;
}
