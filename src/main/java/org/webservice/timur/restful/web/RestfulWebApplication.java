package org.webservice.timur.restful.web;

import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.webservice.timur.restful.web.dao.EntranceServiceEntityRepository;
import org.webservice.timur.restful.web.dao.UserRepository;
import org.webservice.timur.restful.web.model.entity.EntranceServiceEntity;
import org.webservice.timur.restful.web.model.entity.Room;
import org.webservice.timur.restful.web.model.entity.User;

@Log4j2
@SpringBootApplication
public class RestfulWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulWebApplication.class, args);
    }

    /**
     * Инициализация тестовых данных для 5 комнат и 10000 пользователей.
     */
    @Bean
    public CommandLineRunner demo(EntranceServiceEntityRepository repository,
                                  UserRepository userRepository) {
        return (args -> {

            //инициализация 10000 пользователей
            Stream.iterate(1, i -> i = i + 1)
                    .limit(10000)
                    .forEach(a -> userRepository.save(new User("User" + a)));

            //тест успешной инициализации нескольких произвольных пользователей с выводом в логи
            log.info(userRepository.findById(10));
            log.info(userRepository.findByName("User1"));
            log.info(userRepository.findByName("User10000"));

            //инициализации 5 пустых помещений
            repository.save(new EntranceServiceEntity(new Room("Kitchen"), false));
            repository.save(new EntranceServiceEntity(new Room("Sale"), false));
            repository.save(new EntranceServiceEntity(new Room("Corridor"), false));
            repository.save(new EntranceServiceEntity(new Room("Hall"), false));
            repository.save(new EntranceServiceEntity(new Room("Balcony"), false));
            repository.findAll().forEach(log::info);

        });
    }
}
