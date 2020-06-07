package org.webservice.timur.restful.web.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность, содержащая инфо о комнате и пользователе в ней, а также о состоянии занятости.
 */
@Entity
@Data
@NoArgsConstructor
public class EntranceServiceEntity {

    /**
     * Идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    /**
     * Пользователь в комнате.
     */
    @OneToOne
    private User user;

    /**
     * Комната.
     */
    @OneToOne(cascade = {CascadeType.ALL})
    private Room room;

    /**
     * Статус занятости комнаты.
     */
    boolean occupied;

    public EntranceServiceEntity(Room room, boolean occupied) {
        this.room = room;
        this.occupied = occupied;
    }
}
