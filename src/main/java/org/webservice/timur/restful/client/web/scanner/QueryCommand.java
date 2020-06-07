package org.webservice.timur.restful.client.web.scanner;

public interface QueryCommand<T> {
    T get() throws Exception;
}
