package ru.margarita.NauJava.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest {

    private String authToken;
    private final String authCookie = "JSESSIONID";
    private final String nameParam = "name";
    private final String emailParam = "email";
    private final String passwordParam = "password";
    private final String addUrl = "/custom/users";

    private final String addUser = addUrl + "/addUser";
    private final String deleteByName = addUrl + "/deleteByName";

    private final String findByName = addUrl + "/findByName";
    private final String findByEmailAndPassword = addUrl + "/findByEmailAndPassword";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        Response loginResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("username", "admin")
                .formParam("password", "admin")
                .when()
                .post("/login");

        authToken = loginResponse.getCookie(authCookie);
    }

    private Response createUser(String name, String email, String password) {
        return given()
                .cookie(authCookie, authToken)
                .when()
                .param(nameParam, name)
                .param(emailParam, email)
                .param(passwordParam, password)
                .when()
                .post(addUser);
    }

    /**
     * Тестирование поиск пользователя по имени
     * положительный <br>
     * <ol>
     * <b>Подготовка</b>
     * <li>Создать пользователя</li>
     * <br>
     * <b>Действие</b>
     * <li>поиск по имени созданного пользователя</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <li>в возвращаемом списке есть имя пользователя</li>
     * <ol/>
     */
    @Test
    void testFindByNamePositive200() {
        //подготовка
        String name = "TEST";
        createUser(name, "test@example.com", "123");

        //действия и проверки
        given()
                .cookie(authCookie, authToken)
                .param(nameParam, name)
                .when()
                .get(findByName)
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .body("[0].name", equalTo(name));
    }


    /**
     * Тестирование поиск не существующего пользователя
     * отрицательный <br>
     * <ol>
     * <b>Действие</b>
     * <li>поиск по не существующему имени пользователя</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <li>в возвращаемом списке отсутствует имя пользователя</li>
     * <ol/>
     */
    @Test
    void testFindByNameNegative200() {
        //действия и проверки
        given()
                .cookie(authCookie, authToken)
                .param(nameParam, "unknown")
                .when()
                .get(findByName)
                .then()
                .statusCode(200)
                .body("$", hasSize(equalTo(0)));
    }


    /**
     * Тестирование поиск пользователя по его имени, получение ошибки (статус 500) при некорректных параметрах <br>
     * <ol>
     * <b>Действие</b>
     * <li>поиск по имени без параметра имени</li>
     * <br>
     * <b>Проверка</b>
     * <li>статус запроса = 500</li>
     * <ol/>
     */
    @Test
    void testFindByName500() {
        //действия и проверка
        given()
                .cookie(authCookie, authToken)
                .when()
                .get(findByName)
                .then()
                .statusCode(500);
    }

    /**
     * Тестирование поиск пользователя по почте или паролю
     * положительный <br>
     * <ol>
     * <b>Подготовка</b>
     * <li>Создать пользователя</li>
     * <br>
     * <b>Действие</b>
     * <li>поиск по почте и паролю созданного пользователя</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <li>в возвращаемом списке имя и почта полученного пользователя совпадают с созданным</li>
     * <ol/>
     */
    @Test
    void testFindByEmailAndPasswordPositive200() {
        //подготовка
        String email = "test2@example.com";
        String password = "test2";
        String name = "TEST2";
        createUser(name, email, password);
        //действия и проверки
        given()
                .cookie(authCookie, authToken)
                .param(emailParam, email)
                .param(passwordParam, password)
                .when()
                .get(findByEmailAndPassword)
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)))
                .body("[0].name", equalTo(name))
                .body("[0].email", equalTo(email));
    }

    /**
     * Тестирование поиск пользователя по не существующим почте или паролю
     * негативный <br>
     * <ol>
     * <b>Действие</b>
     * <li>поиск по не существующей почте и паролю пользователя</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <li>возвращаемый список пуст</li>
     * <ol/>
     */
    @Test
    void testFindByEmailAndPasswordNegative200() {
        //действия и проверки
        given().cookie(authCookie, authToken)
                .param(emailParam, "wrong@example.com")
                .param(passwordParam, "wrongpassword")
                .when()
                .get(findByEmailAndPassword)
                .then()
                .statusCode(200)
                .body("$", hasSize(equalTo(0)));
    }

    /**
     * Тестирование поиск пользователя по почте или паролю, получение ошибки (статус 500), при неправильных параметрах <br>
     * <ol>
     * <b>Действие</b>
     * <li>поиск по только по почте или только по паролю пользователя</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 500</li>
     * <ol/>
     */
    @Test
    void testFindByEmailAndPassword500() {
        //действия и проверка при отсутствии почты
        given().cookie(authCookie, authToken)
                .param(passwordParam, "somepassword")
                .when()
                .get(findByEmailAndPassword)
                .then()
                .statusCode(500);
        //действия и проверка при отсутствии пароля
        given().cookie(authCookie, authToken)
                .param(emailParam, "test@example.com")
                .when()
                .get(findByEmailAndPassword)
                .then()
                .statusCode(500);
    }

    /**
     * Тестирование добавление пользователя <br>
     * <ol>
     * <b>Действие</b>
     * <li>добавление пользователя (передаются имя, почта и пароль)</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <ol/>
     */
    @Test
    void testAddUser200() {
        //действия и проверка
        createUser("TestUser", "testuser@example.com", "testpass")
                .then()
                .statusCode(200);
    }

    /**
     * Тестирование добавление пользователя, получение ошибки, при некорректных параметрах <br>
     * <ol>
     * <b>Действие</b>
     * <li>добавление пользователя, три случая, без одного из параметров (имя, почта и пароль)</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 500</li>
     * <ol/>
     */
    @Test
    void testAddUser500() {
        String name = "TestUser";
        String email = "testuser@example.com";
        String password = "testpass";
        //действия и проверка при отсутствии имени
        given()
                .cookie(authCookie, authToken)
                .param(emailParam, email)
                .param(passwordParam, password)
                .when()
                .post(addUser)
                .then()
                .statusCode(500);
        //действия и проверка при отсутствии почты
        given()
                .cookie(authCookie, authToken)
                .param(nameParam, name)
                .param(passwordParam, password)
                .when()
                .post(addUser)
                .then()
                .statusCode(500);
        //действия и проверка при отсутствии пароля
        given()
                .cookie(authCookie, authToken)
                .param(nameParam, name)
                .param(emailParam, email)
                .when()
                .post(addUser)
                .then()
                .statusCode(500);
    }

    /**
     * Тестирование удаления существующего пользователя
     * положительный <br>
     * <ol>
     * <b>Подготовка</b>
     * <li>Создать пользователя</li>
     * <br>
     * <b>Действие</b>
     * <li>удаление пользователя по имени</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <li>отсутствие при дальнейшем поиске по имени</li>
     * <ol/>
     */
    @Test
    void testDeleteByNamePositive() {
        //подготовка
        String name = "UserToDelete";
        String email = "delete@example.com";
        String password = "deletepass";
        createUser(name, email, password);

        Response responseBefore = given()
                .cookie(authCookie, authToken)
                .param(nameParam, name)
                .when()
                .get(findByName);

        List<?> usersBefore = responseBefore.jsonPath().getList("$");
        assertFalse(usersBefore.isEmpty(), "User should exist before deletion");

        //действие и проверка
        given()
                .cookie(authCookie, authToken)
                .param(nameParam, name)
                .when()
                .delete(deleteByName)
                .then()
                .statusCode(200);

        //проверки
        Response responseAfter = given()
                .cookie(authCookie, authToken)
                .param(nameParam, name)
                .when()
                .get(findByName);

        List<?> usersAfter = responseAfter.jsonPath().getList("$");
        assertEquals(0, usersAfter.size(), "User should be deleted");
    }

    /**
     * Тестирование удаления пользователя, удаление не существующего пользователя
     * негативный <br>
     * <ol>
     * <b>Действие</b>
     * <li>удаление не существующего пользователя</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 200</li>
     * <ol/>
     */
    @Test
    void testDeleteByNameNegative() {
        //действие и проверка
        given()
                .cookie(authCookie, authToken)
                .param(nameParam, "NonExistentUserForDeletion")
                .when()
                .delete(deleteByName)
                .then()
                .statusCode(200);
    }


    /**
     * Тестирование удаления пользователя, получение ошибки(статус 500) при не передаче параметра<br>
     * <ol>
     * <b>Действие</b>
     * <li>удаление пользователя без указания имени</li>
     * <br>
     * <b>Проверки</b>
     * <li>статус запроса = 500</li>
     * <ol/>
     */
    @Test
    void testDeleteByName500() {
        //действие и проверка
        given()
                .cookie(authCookie, authToken)
                .when()
                .delete(deleteByName)
                .then()
                .statusCode(500);
    }
}