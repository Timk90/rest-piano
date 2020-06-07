package org.webservice.timur.restful.web.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.webservice.timur.restful.web.exceptionhandler.exception.InternalServerException;
import org.webservice.timur.restful.web.exceptionhandler.exception.NoEmptyRoomAvailableException;
import org.webservice.timur.restful.web.exceptionhandler.exception.RoomHasAnotherOccupationStateException;
import org.webservice.timur.restful.web.exceptionhandler.exception.UserAlreadyOccupiedAnotherRoomException;
import org.webservice.timur.restful.web.exceptionhandler.exception.UserNotFoundException;
import org.webservice.timur.restful.web.exceptionhandler.exception.UserNotFoundInRoomException;
import org.webservice.timur.restful.web.model.response.Response;

/**
 * Контроллер с аспектами обрабатывающими различные ошибки (исключение).
 * Формирует ответ с ошибкой на запрос клиента.
 */
@ControllerAdvice
public class MainAdviceExceptionHandler {

    /**
     * Обрабаотывает ошибки типа "Пользователь е найден в БД".
     *
     * @param ex исключение
     * @return ответ с ошибкой клиенту
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Response> userNotFoundHandler(UserNotFoundException ex) {
        final Response error = Response.builder().build();
        error.setHttpStatusLabel(HttpStatus.FORBIDDEN.toString());
        error.setHttpStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage("Error while searching for user. \n Error message: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабаотывает ошибки типа "Пользователь не найден в указанной комнате.
     *
     * @param ex исключение
     * @return ответ с ошибкой клиенту
     */
    @ResponseBody
    @ExceptionHandler(UserNotFoundInRoomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> userNotFoundInRoomHandler(UserNotFoundInRoomException ex) {
        final Response error = Response.builder().build();
        error.setHttpStatusLabel(HttpStatus.FORBIDDEN.toString());
        error.setHttpStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage("Error while leaving room. \n Error message: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабаотывает ошибки типа "Нет свободных комнат".
     *
     * @param ex исключение
     * @return ответ с ошибкой клиенту
     */
    @ResponseBody
    @ExceptionHandler(NoEmptyRoomAvailableException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> noAvailableRoomHandler(NoEmptyRoomAvailableException ex) {
        final Response error = Response.builder().build();
        error.setHttpStatusLabel(HttpStatus.FORBIDDEN.toString());
        error.setHttpStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage("Error while entering room. \n Error message: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабаотывает ошибки типа "Пользователь уже занял другую комнату".
     *
     * @param ex исключение
     * @return ответ с ошибкой клиенту
     */
    @ResponseBody
    @ExceptionHandler(UserAlreadyOccupiedAnotherRoomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> userInAnotherRoomHandler(UserAlreadyOccupiedAnotherRoomException ex) {
        final Response error = Response.builder().build();
        error.setHttpStatusLabel(HttpStatus.FORBIDDEN.toString());
        error.setHttpStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage("Error while entering room. User found in another room. " +
                "\n Error message: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабаотывает ошибки типа "Проверка состояния комнаты с для заданного пользователя окончилась неудачей".
     * Пользователь не занимал указанную комнату или наоборот занял ее, в то время как мы запросили отличый статус.
     * Также ошибка возникает, если данная комната уже была занята кем-то другим, а не указанным пользователем
     * или пользователь находится в другой комнате сейчас.
     *
     * @param ex исключение
     * @return ответ с ошибкой клиенту
     */
    @ResponseBody
    @ExceptionHandler(RoomHasAnotherOccupationStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> roomHasAnotherOccupationHandler(RoomHasAnotherOccupationStateException ex) {
        final Response error = Response.builder().build();
        error.setHttpStatusLabel(HttpStatus.FORBIDDEN.toString());
        error.setHttpStatus(HttpStatus.FORBIDDEN.value());
        error.setMessage("Error while check room state. " +
                "\n Error message: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Обрабаотывает ошибки типа "Внутренняя ошибка сервера".
     * Для тестового сценария таким ответом помечены ошибки, когда пользователь или комната не существуют.
     *
     * @param ex исключение
     * @return ответ с ошибкой клиенту
     */
    @ResponseBody
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> serverHasNotAnsweredHandler(InternalServerException ex) {
        final Response error = Response.builder().build();
        error.setHttpStatusLabel(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Error for the specified server request. \n Error message: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
