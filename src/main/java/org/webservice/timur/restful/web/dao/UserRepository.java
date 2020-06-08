package org.webservice.timur.restful.web.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.webservice.timur.restful.web.model.entity.User;

/**
 * Репозиторий для взаимодействия с БД хранящей сущности пользователей.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Поиск пользователя по имени.
     *
     * @param name имя
     * @return пользователя с указанным именем
     */
    List<User> findByName(String name);

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param id идентификатор
     * @return пользователь с указанным идентификатором
     */
    User findById(long id);
}
