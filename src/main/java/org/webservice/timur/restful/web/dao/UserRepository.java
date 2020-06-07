package org.webservice.timur.restful.web.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.webservice.timur.restful.web.model.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);

    User findById(long id);
}
