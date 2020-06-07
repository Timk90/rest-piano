package org.webservice.timur.restful.web.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.webservice.timur.restful.web.model.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {

    List<Room> findByName(String name);

    Room findById(long id);

    List<Room> findAll();

}