package org.webservice.timur.restful.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.webservice.timur.restful.web.controller.MainRestController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Здесь конечно должны быть тесты на все сценарии, но я что-то и так потратил слишком много времени на задание.
 * Это обсуждается при желании. Тест писатьс умеем вроде бысь...
 */
@SpringBootTest
@AutoConfigureMockMvc
class RestfulWebApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MainRestController controller;

    /**
     * Тестирование метода проверяющего занятость комнтаы пользователем.
     *
     * @throws Exception при ошибке
     */
    @Test
    void testCheckMethod() throws Exception {
        String urlString = "?roomId=1&entrance=true&keyId=1";
        this.mockMvc.perform(get("/check" + urlString)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Room 1 is empty.")));
    }

}
