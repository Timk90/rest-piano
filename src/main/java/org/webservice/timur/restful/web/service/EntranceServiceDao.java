package org.webservice.timur.restful.web.service;

import java.util.List;
import org.webservice.timur.restful.web.model.entity.EntranceServiceEntity;

/**
 * Сервис для работы с запросами к серверу и репозиториями сущностей польщзователя и комнаты.
 */
public interface EntranceServiceDao {

    /**
     * Проверка занятости комнаты.
     *
     * @param roomId   идентифиатор комнаты
     * @param keyId    идентификатор пользователя
     * @param entrance предполагаемое состояние комнаты (возможность входа)
     * @return сущность, содержащую данные о комнате и пользователе в ней
     */
    EntranceServiceEntity checkRoomOccupation(long roomId, long keyId, boolean entrance);

    /**
     * Анализ возможности войти в свободную комнату для пользователя с указанным id.
     * Осуществление входа в комнату при наоичии возможности.
     *
     * @param keyId идентификатор пользователя
     * @return сущность, содержащую данные о комнате и пользователе в ней
     */
    EntranceServiceEntity enterRoom(long keyId);

    /**
     * Анализ возможности из ранее занятой комнаты для пользователя с указанным id.
     * Осуществление выхода из комнаты, если ранее он был осуществлен.
     *
     * @param keyId идентифитор пользователя.
     * @return сущность, содержащую данные о комнате и пользователе в ней
     */
    EntranceServiceEntity leaveRoom(long keyId);

    /**
     * Метод для тестирования заселенности всех комнат пользователями.
     * Необходим только для тестов.
     *
     * @return все сущности, содержащие данные о комнатах и пользователях в них
     */
    List<EntranceServiceEntity> findAll();
}
