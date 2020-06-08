package org.webservice.timur.restful.web.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.webservice.timur.restful.web.model.entity.EntranceServiceEntity;
import org.webservice.timur.restful.web.model.entity.Room;
import org.webservice.timur.restful.web.model.entity.User;

/**
 * Репозиторий для взаимодействия с БД хранящей объединенные сущности
 * помещений (комнат){@link Room} и пользователей {@link User}.
 */
public interface EntranceServiceEntityRepository extends CrudRepository<EntranceServiceEntity, Long> {

    /**
     * Найти сущность для указанного пользователя.
     *
     * @param user пользователь
     * @return представление, если пользователь занимает одну из комнат
     */
    List<EntranceServiceEntity> findByUser(User user);

    /**
     * Найти сущность для указанной комнаты.
     *
     * @param room комната
     * @return представление, содержащее одну из комнат
     */
    List<EntranceServiceEntity> findByRoom(Room room);

    /**
     * Найти сущность для указанной комнаты, если ее состояние соответствует параметру.
     *
     * @param room комната
     * @return представление, содержащее одну из комнат с указанным парамтером занятости
     */
    List<EntranceServiceEntity> findByRoomAndOccupied(Room room, boolean occupied);

    /**
     * Найти все записи, содеражащие все доступные помещения и пользователей в них с указанием статуса занятости.
     *
     * @return все сущности
     */
    List<EntranceServiceEntity> findAll();

}
