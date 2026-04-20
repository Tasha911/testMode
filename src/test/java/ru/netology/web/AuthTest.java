package ru.netology.web;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static ru.netology.web.RegistrationDto.STATUS_ACTIVE;
import static ru.netology.web.RegistrationDto.STATUS_BLOCKED;

// спецификация нужна для того, чтобы переиспользовать настройки в разных запросах
class AuthTest {

    public static final String CREATE_USER_ENDPOINT = "/api/system/users";

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Test
    void testSuccessCreateActiveUser() {
        RegistrationDto user = AuthTestDataGenerator.generateUser("en", STATUS_ACTIVE);
        checkCreateUserRequest(user, 200);
    }

    @Test
    void testSuccessCreateBlockedUser() {
        RegistrationDto user = AuthTestDataGenerator.generateUser("en", STATUS_BLOCKED);
        checkCreateUserRequest(user, 200);
    }

    @Test
    void testSuccessCreateActiveUserTwiceSameLogin() {
        String login = AuthTestDataGenerator.generateLogin("en");

        RegistrationDto user = AuthTestDataGenerator.generateUser("en", login, STATUS_ACTIVE);
        checkCreateUserRequest(user, 200);

        RegistrationDto user2 = AuthTestDataGenerator.generateUser("en", login, STATUS_ACTIVE);
        checkCreateUserRequest(user2, 200);
    }

    @Test
    void testSuccessCreateBlockedUserTwiceSameLogin() {
        String login = AuthTestDataGenerator.generateLogin("en");

        RegistrationDto user = AuthTestDataGenerator.generateUser("en", login, STATUS_BLOCKED);
        checkCreateUserRequest(user, 200);

        RegistrationDto user2 = AuthTestDataGenerator.generateUser("en", login, STATUS_BLOCKED);
        checkCreateUserRequest(user2, 200);
    }

    @Test
    void testSuccessCreateUserTwiceSameLoginChangeStatus() {
        String login = AuthTestDataGenerator.generateLogin("en");

        RegistrationDto user = AuthTestDataGenerator.generateUser("en", login, STATUS_BLOCKED);
        checkCreateUserRequest(user, 200);

        RegistrationDto user2 = AuthTestDataGenerator.generateUser("en", login, STATUS_ACTIVE);
        checkCreateUserRequest(user2, 200);
    }

    @Test
    void testSuccessCreateUserTwiceSameLoginChangeStatus2() {
        String login = AuthTestDataGenerator.generateLogin("en");

        RegistrationDto user = AuthTestDataGenerator.generateUser("en", login, STATUS_ACTIVE);
        checkCreateUserRequest(user, 200);

        RegistrationDto user2 = AuthTestDataGenerator.generateUser("en", login, STATUS_BLOCKED);
        checkCreateUserRequest(user2, 200);
    }

    private void checkCreateUserRequest(RegistrationDto userDto, int expectedStatus) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                // передаём в теле объект, который будет преобразован в JSON
                .body(userDto)
                .when() // "когда"
                // на какой путь относительно BaseUri отправляем запрос
                .post(CREATE_USER_ENDPOINT)
                .then() // "тогда ожидаем"
                .statusCode(expectedStatus); // код 200 OK
    }

}
