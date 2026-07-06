package by.kufar.api;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    final String LOGIN_URL = "https://cre-auth.kufar.by/v2/auth/signin?token_type=user";

    @Test
    public void testLoginWithEmptyBody(){
        String body= "{\n" +
                "    \"login\": \"\",\n" +
                "    \"password\": \"\"\n" +
                "}";
        given()
                .header("content-type", "application/json;charset=UTF-8")
                .body(body)
        .when()
                .post(LOGIN_URL)
        .then()
                .log().all()
                .statusCode(400)
                //.body("errors.password[0]", equalTo("Введите пароль"))
                .body("label.text", equalTo("Не заполнено обязательное поле"));
    }

    @Test
    public void testLoginWithoutPassword(){
        Faker faker = new Faker();
        String emiL = faker.internet().emailAddress();

        String body= "{\n" +
                "    \"login\": \""+ emiL +"\",\n" +
                "    \"password\": \"\"\n" +
                "}";
        given()
                .header("content-type", "application/json;charset=UTF-8")
                .body(body)
        .when()
                .post(LOGIN_URL)
        .then()
                .statusCode(400)
                .body("label.text", equalTo("Не заполнено обязательное поле"));
    }

    @Test
    public void testLoginWithoutEmail(){
        Faker faker = new Faker();
        String password = faker.internet().password();

        String body= "{\n" +
                "    \"login\": \"\",\n" +
                "    \"password\": \""+ password +"\"\n" +
                "}";                ;
        given()
                .header("content-type", "application/json;charset=UTF-8")
                .body(body)
        .when()
                .post(LOGIN_URL)
        .then()
                .statusCode(400)
                .body("label.text", equalTo("Не заполнено обязательное поле"));
    }
}
