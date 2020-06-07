package org.webservice.timur.restful.web.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.webservice.timur.restful.web.model.entity.EntranceServiceEntity;
import org.webservice.timur.restful.web.model.entity.Room;
import org.webservice.timur.restful.web.model.entity.User;

public interface EntranceServiceEntityRepository extends CrudRepository<EntranceServiceEntity, Long> {

    List<EntranceServiceEntity> findByUser(User user);

    List<EntranceServiceEntity> findByRoom(Room room);

    EntranceServiceEntity findById(long id);

    List<EntranceServiceEntity> findByRoomAndOccupied(Room room, boolean occupied);

    List<EntranceServiceEntity> findAll();

}
