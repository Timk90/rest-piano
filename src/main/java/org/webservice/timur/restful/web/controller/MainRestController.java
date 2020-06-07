package org.webservice.timur.restful.web.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webservice.timur.restful.web.model.entity.EntranceServiceEntity;
import org.webservice.timur.restful.web.model.response.Response;
import org.webservice.timur.restful.web.service.EntranceServiceDao;

/**
 * Контроллер обрабатывает входящие http запросы.
 */
@Log4j2
@RestController
public class MainRestController {

    @Autowired
    EntranceServiceDao entranceServiceDao;

    /**
     * Проверка того, занята ли комната или свободна пользователем
     * с уазанным значением id.
     *
     * @param roomId   идентификатор комнаты
     * @param occupied статус занятости
     * @param keyId    идентификатор пользователя
     * @return
     */
    @GetMapping("/check")
    public ResponseEntity<Response> check(
            @RequestParam(value = "roomId") long roomId,
            @RequestParam(value = "entrance") boolean occupied,
            @RequestParam(value = "keyId") long keyId
    ) {
        //пример запроса к серверу
        //http://localhost:8080/check?roomId=1&entrance=true&keyId=1
        log.info("Query of the room occupation check " + roomId + " by user with id " + keyId + " initialized.");

        EntranceServiceEntity entity = entranceServiceDao.checkRoomOccupation(roomId, keyId, occupied);
        Response response = Response.builder().build();

        if (entity.getRoom() != null && !occupied) {
            response.setHttpStatus(HttpStatus.OK.value());
            response.setHttpStatusLabel(HttpStatus.OK.toString());
            response.setMessage("Room " + roomId + " is empty.");
        } else {
            response.setHttpStatus(HttpStatus.FORBIDDEN.value());
            response.setHttpStatusLabel(HttpStatus.FORBIDDEN.toString());
            response.setMessage("Room " + roomId + " is occupied.");
        }
        response.setRoom(entity.getRoom());
        response.setUser(entity.getUser());
        log.info("Query of the room occupation check " + roomId + " by user with id " + keyId + " finished.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/enter/{id}")
    public ResponseEntity<Response> enter(
            @PathVariable(value = "id") long id
    ) {
        EntranceServiceEntity entity = entranceServiceDao.enterRoom(id);
        Response response = Response.builder().build();
        response.setMessage("User enter the room.");
        response.setHttpStatus(HttpStatus.OK.value());
        response.setHttpStatusLabel(HttpStatus.OK.toString());
        response.setUser(entity.getUser());
        response.setRoom(entity.getRoom());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/leave/{id}")
    public ResponseEntity<Response> leave(
            @PathVariable(value = "id") long id
    ) {
        EntranceServiceEntity entity = entranceServiceDao.leaveRoom(id);
        Response response = Response.builder().build();
        response.setMessage("User leaved the room.");
        response.setHttpStatus(HttpStatus.OK.value());
        response.setHttpStatusLabel(HttpStatus.OK.toString());
        response.setUser(entity.getUser());
        response.setRoom(entity.getRoom());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/checkAll")
    public ResponseEntity<List<EntranceServiceEntity>> checkAll() {
        return new ResponseEntity<>(entranceServiceDao.findAll(), HttpStatus.OK);
    }
}
