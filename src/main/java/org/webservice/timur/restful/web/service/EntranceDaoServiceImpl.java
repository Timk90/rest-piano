package org.webservice.timur.restful.web.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webservice.timur.restful.web.dao.EntranceServiceEntityRepository;
import org.webservice.timur.restful.web.dao.RoomRepository;
import org.webservice.timur.restful.web.dao.UserRepository;
import org.webservice.timur.restful.web.exceptionhandler.exception.InternalServerException;
import org.webservice.timur.restful.web.exceptionhandler.exception.NoEmptyRoomAvailableException;
import org.webservice.timur.restful.web.exceptionhandler.exception.RoomHasAnotherOccupationStateException;
import org.webservice.timur.restful.web.exceptionhandler.exception.UserAlreadyOccupiedAnotherRoomException;
import org.webservice.timur.restful.web.exceptionhandler.exception.UserNotFoundException;
import org.webservice.timur.restful.web.exceptionhandler.exception.UserNotFoundInRoomException;
import org.webservice.timur.restful.web.model.entity.EntranceServiceEntity;
import org.webservice.timur.restful.web.model.entity.Room;
import org.webservice.timur.restful.web.model.entity.User;
import org.webservice.timur.restful.web.service.types.RoomState;

/**
 * Имплементация для {@link EntranceServiceDao}
 */
@Log4j2
@Service
public class EntranceDaoServiceImpl implements EntranceServiceDao {

    /**
     * Для работы с репозиториями всех сущностей.
     */
    UserRepository userRepository;
    EntranceServiceEntityRepository entranceServiceEntityRepository;
    RoomRepository roomRepository;

    @Autowired
    public EntranceDaoServiceImpl(UserRepository userRepository,
                                  EntranceServiceEntityRepository entranceServiceEntityRepository,
                                  RoomRepository roomRepository
    ) {
        this.userRepository = userRepository;
        this.entranceServiceEntityRepository = entranceServiceEntityRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public EntranceServiceEntity checkRoomOccupation(long roomId, long keyId, boolean entrance) {

        //внутреннее представление формата хранения данных немного отличается.
        //Если можно войти в дверь, значит там не занято, т.е. значение occupied = false.
        boolean occupied = !entrance;

        Room room = roomRepository.findById(roomId);
        if (room != null) {
            log.info("Room retrieved from DB by Id {} is {}.", room.getId(), room.getName());
        } else {
            log.info("Room with Id {} has not been found in DB.", roomId);
            throw new InternalServerException("Room " + roomId + " not found.");
        }

        User user = userRepository.findById(keyId);
        if (user != null) {
            log.info("User retrieved from DB by Id {} has Name: {}.", user.getId(), user.getName());
        } else {
            log.info("User with Id {} has not been found in DB.", keyId);
            throw new UserNotFoundException("Not found by id " + keyId);
        }

        List<EntranceServiceEntity> entityRoomOccupation = entranceServiceEntityRepository
                .findByRoomAndOccupied(room, occupied);

        List<EntranceServiceEntity> entityRoomOccupationByUser = entranceServiceEntityRepository
                .findByUser(user);

        if (!entityRoomOccupation.isEmpty()) {
            EntranceServiceEntity entity = entityRoomOccupation.get(0);
            if (occupied) {
                if (entity.getUser().getId() == user.getId()) {
                    log.info("Room {} is actually occupied by User(id:{}, name:{}). You may leave the room.",
                            entity.getRoom().getName(), user.getId(), user.getName());
                    return entity;
                } else {
                    log.info("Room {} isn't occupied by User(id:{},name:{}) however occupied by User(id:{},name:{})",
                            entity.getRoom().getName(), user.getId(), user.getName(),
                            entity.getUser().getId(), entity.getUser().getName());
                    throw new RoomHasAnotherOccupationStateException("Room " + entity.getRoom().getName()
                            + " isn't occupied by User(id:" + user.getId() + ", name:" + user.getName()
                            + "), however occupied by User(id:" + entity.getUser().getId() + ", name:"
                            + entity.getUser().getName() + ").");
                }
            } else {
                if (entity.getUser() == null) {
                    if (entityRoomOccupationByUser.isEmpty()) {
                        log.info("Room {} is free to be occupied by User(id:{}, name:{})",
                                entity.getRoom().getName(), user.getId(), user.getName());
                    } else {
                        EntranceServiceEntity entityUser = entityRoomOccupationByUser.get(0);
                        log.info("Room {} is free however User(id:{}, name:{}) occupies another Room(id:{},name:{})",
                                entity.getRoom().getName(), user.getId(), user.getName(),
                                entityUser.getRoom().getId(), entityUser.getRoom().getName());
                    }
                    return entity;
                }
            }
        } else {
            RoomState state = !occupied ? RoomState.OCCUPIED : RoomState.EMPTY;
            log.info("Room #{}[{}] with the occupation state {} has not been found.",
                    room.getId(), room.getName(), state.name());
            throw new RoomHasAnotherOccupationStateException("Room has the occupation state " + state.name());
        }
        return null;
    }

    @Override
    public EntranceServiceEntity enterRoom(long keyId) {
        User user = userRepository.findById(keyId);
        if (user != null) {
            log.info("User retrieved from DB by Id {} has Name: {}.", user.getId(), user.getName());
        } else {
            log.info("User with Id {} has not been found in DB", keyId);
            throw new UserNotFoundException("Not found by id " + keyId);
        }
        if (!entranceServiceEntityRepository.findByUser(user).isEmpty()) {
            log.info("User with Id {} has not been found in DB", keyId);
            throw new UserAlreadyOccupiedAnotherRoomException("User with id " + keyId + " is found in another room.");
        }
        int roomNumber = calculateRoomNumberForKeyID(keyId);
        if (roomNumber > 0) {
            Room room = roomRepository.findById(roomNumber);
            List<EntranceServiceEntity> entities = entranceServiceEntityRepository.findByRoom(room);
            if (!entities.isEmpty()) {
                EntranceServiceEntity entity = entities.get(0);
                if (entity.getUser() != null) {
                    throw new NoEmptyRoomAvailableException("No available room is present at the moment. Room #"
                            + room.getId() + " is occupied by " + entity.getUser());
                }
                entity.setUser(user);
                entity.setOccupied(true);
                entranceServiceEntityRepository.save(entity);
                log.info("User: id = {}, name = {} has entered the room: id = {}, name = {}. ",
                        user.getId(), user.getName(), room.getId(), room.getName());
                return entity;
            } else {
                throw new InternalServerException("Error while retrieving entities to enter the room #"
                        + roomNumber + ". The room does not exist or has been occupied during the query.");
            }
        } else {
            log.info("User: id = {}, name = {} cannot enter any room. All rooms are occupied at the moment.",
                    user.getId(), user.getName());
            throw new NoEmptyRoomAvailableException("No available room presents at the moment.");
        }
    }

    @Override
    public EntranceServiceEntity leaveRoom(long keyId) {
        EntranceServiceEntity entity;
        User user = userRepository.findById(keyId);
        if (user == null) {
            log.info("User with id {} has not been found", keyId);
            throw new UserNotFoundException("User has not been found in DB by id " + keyId);
        }
        List<EntranceServiceEntity> entities = entranceServiceEntityRepository.findByUser(user);
        if (!entities.isEmpty()) {
            entity = entranceServiceEntityRepository.findByUser(user).get(0);
            log.info("User[id:{}, name:{}] has been found in room[id:{}, name:{}]",
                    user.getId(), user.getName(), entity.getRoom().getId(), entity.getRoom().getName());
        } else {
            log.info("User[id:{}, name:{}] has been found in any room", user.getId(), user.getName());
            throw new UserNotFoundInRoomException("User with id " + keyId + " has not been found in any room.");
        }
        entity.setUser(null);
        entity.setOccupied(false);
        entranceServiceEntityRepository.save(entity);
        log.info("User: id = {}, name = {} has leaved the room: id = {}, name = {}. ",
                user.getId(), user.getName(), entity.getRoom().getId(), entity.getRoom().getName());
        return entity;
    }

    private int calculateRoomNumberForKeyID(long keyId) {
        List<Room> rooms = roomRepository.findAll();
        List<EntranceServiceEntity> entities = entranceServiceEntityRepository.findAll();
        int numberOfRooms = rooms.size();

        Map<Long, EntranceServiceEntity> entityMap = entities.stream()
                .collect(Collectors.toMap(ent -> ent.getRoom().getId(), value -> value));

        Optional<Room> room = Stream.iterate(numberOfRooms, i -> i -= 1)
                .limit(numberOfRooms)
                .map(i -> entityMap.get(Long.valueOf(i)))
                .filter(Objects::nonNull)
                .filter(e -> keyId % e.getRoom().getId() == 0)
                .filter(e -> !e.isOccupied())
                .map(EntranceServiceEntity::getRoom)
                .findFirst();
        return room.map(value -> (int) value.getId()).orElse(0);
    }

    @Override
    public List<EntranceServiceEntity> findAll() {
        return entranceServiceEntityRepository.findAll();
    }
}
