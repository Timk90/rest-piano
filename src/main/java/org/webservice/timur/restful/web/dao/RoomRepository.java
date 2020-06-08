package org.webservice.timur.restful.web.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.webservice.timur.restful.web.model.entity.Room;

/**
 * Репозиторий для взаимодействия с БД хранящей сущности помещений (комнат).
 */
public interface RoomRepository extends CrudRepository<Room, Long> {

    /**
     * Найти комнату по имени.
     *
     * @param name имя
     * @return комната с указанным именем
     */
    List<Room> findByName(String name);

    /**
     * Найти комнату по идентификатору.
     *
     * @param id идентификатор
     * @return комната с указанным именем
     */
    Room findById(long id);

    /**
     * Найти все доступные комнаты.
     *
     * @return список комнат
     */
    List<Room> findAll();

}