package org.webservice.timur.restful.client.web.scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.extern.log4j.Log4j2;
import org.webservice.timur.restful.client.web.scanner.exception.EntranceQueryException;
import org.webservice.timur.restful.web.model.response.Response;

/**
 * Простой сканер (клиент) для отправки запросов на наш сервис.
 * Осуществляет также парсинг JSON ответов в POJO.
 */
@Log4j2
public class Scanner {

    /**
     * Запрос на проверку состояния комнаты.
     *
     * @param roomId   идентификатор комнаты, которую нужно проверить.
     * @param id       идентификатор пользоваталя для которого мы проверяем статус данной комнаты.
     * @param occupied проверяем комнату для одного из режимов - занята/свободна.
     * @return строка с ответом сервера.
     */
    public String sendCheckRequest(long roomId, long id, boolean occupied) {
        return handleQueryException(() -> {
            String resultStr = "";

            //Произвольный пример запроса
            //http://localhost:8080/check?roomId=1&entrance=false&keyId=19231123213213
            URL url = new URL("http://localhost:8080/check?"
                    + "roomId=" + roomId
                    + "&entrance=" + occupied
                    + "&keyId=" + id);

            resultStr = handleQuery(url);
            return resultStr;
        });
    }

    /**
     * Запрос на поиск комнаты и входа в нее пользователем с указанным ID.
     *
     * @param keyId идентификатор пользователя.
     * @return строка с ответом сервера.
     */
    public String enterRoom(long keyId) {
        return handleQueryException(() -> {
            String resultStr = "";
            URL url = new URL("http://localhost:8080/enter/" + keyId);
            resultStr = handleQuery(url);
            return resultStr;
        });
    }

    /**
     * Запрос на выход из комнаты пользователем с указанным ID (если был осуществлен вход).
     *
     * @param keyId идентификатор пользователя.
     * @return строка с ответом сервера.
     */
    public String leaveRoom(long keyId) {
        return handleQueryException(() -> {
            String resultStr = "";
            URL url = new URL("http://localhost:8080/leave/" + keyId);
            resultStr = handleQuery(url);
            return resultStr;
        });
    }

    /**
     * Основная работа и обработка запросов к серверу.
     *
     * @param url url сервера для запроса
     * @return строка с ответом сервера.
     */
    private String handleQuery(URL url) {
        return handleQueryException(() -> {
            String resultStr = "";
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            System.out.println(conn.getResponseCode());
            conn.getHeaderFields().entrySet().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));

            BufferedReader br;
            //Проверка, является ли код кодом ошибки
            if (conn.getResponseCode() > 299) {
                br = new BufferedReader(new InputStreamReader(
                        (conn.getErrorStream())));
            } else {
                br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
            }

            String output;
            StringBuilder sb = new StringBuilder();
            System.out.println("Output from Server:");
            while ((output = br.readLine()) != null) {
                sb.append(output);
                System.out.println(output);
            }
            conn.disconnect();
            resultStr = sb.toString();
            return resultStr;
        });
    }

    /**
     * Конвертация JSON в бизнес-сущность.
     *
     * @param str строковое представление json объекта для десериализации.
     * @return ответ
     */
    public Response convert(String str) {
        ObjectMapper mapper = new ObjectMapper();
        Response response = null;
        try {
            response = mapper.readValue(str, Response.class);
        } catch (JsonProcessingException e) {
            log.error("Error while JsonProcessing occurred {}", e.getCause());
        }
        return response;
    }

    /**
     * Обертка над всеми исключениями, чтобы проглотить их.
     * Реализовано для тестов.
     *
     * @param supplier функциональный интерфейс
     * @param <T>      параметр типа
     * @return результат того же типа
     */
    private static <T> T handleQueryException(QueryCommand<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("Error occurred while query sending. ");
            throw new EntranceQueryException(e.getMessage());
        }
    }

    /**
     * Жизнь коротка... слишком много времени отняло у меня задание...
     * Поэтому тесты писать не стал, если нужно будет, тогда можно будет добавить.
     * Пока можно запустить main при стартованном сервере.
     * TODO может быть добавить тесты, хз...
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner();
        String str = scanner.sendCheckRequest(8, 1, false);
        Response response = scanner.convert(str);
        System.out.println(response);
        String str1 = scanner.sendCheckRequest(1, 10, true);
        response = scanner.convert(str1);
        System.out.println(response);
        String str0 = scanner.sendCheckRequest(1, 10, false);
        response = scanner.convert(str0);
        System.out.println(response);
        String str2 = scanner.sendCheckRequest(2, 30000, false);
        response = scanner.convert(str2);
        System.out.println(response);
        String str3 = scanner.enterRoom(2);
        response = scanner.convert(str3);
        System.out.println(response);
        String str4 = scanner.leaveRoom(2);
        response = scanner.convert(str4);
        System.out.println(response);
        String str5 = scanner.leaveRoom(2);
        response = scanner.convert(str5);
        System.out.println(response);
        String str6 = scanner.leaveRoom(1000000);
        response = scanner.convert(str6);
        System.out.println(response);
        String str7 = scanner.enterRoom(10000);
        response = scanner.convert(str7);
        System.out.println(response);
        String str8 = scanner.enterRoom(10000);
        response = scanner.convert(str8);
        System.out.println(response);
        String str88 = scanner.sendCheckRequest(1, 10000, true);
        response = scanner.convert(str88);
        System.out.println(response);
        String str888 = scanner.sendCheckRequest(2, 10000, true);
        response = scanner.convert(str888);
        System.out.println(response);
        String str9 = scanner.leaveRoom(10000);
        response = scanner.convert(str9);
        System.out.println(response);

        for (int i = 1; i < 500; i++) {
            String strX = scanner.enterRoom(i);
            response = scanner.convert(strX);
            System.out.println(response);
        }
    }
}
