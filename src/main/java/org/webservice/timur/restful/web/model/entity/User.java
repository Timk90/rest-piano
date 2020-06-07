package org.webservice.timur.restful.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность пользователя.
 */
@Entity
@Data
@NoArgsConstructor
public class User {

    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Имя пользователя.
     */
    private String name;

    public User(String name) {
        this.name = name;
    }
}
