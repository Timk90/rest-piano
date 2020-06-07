package org.webservice.timur.restful.web.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность комнаты.
 */
@Entity
@Data
@NoArgsConstructor
public class Room {

    /**
     * Уникальный идентификатор комнаты.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Название комнты.
     */
    String name;

    public Room(String name) {
        this.name = name;
    }
}
