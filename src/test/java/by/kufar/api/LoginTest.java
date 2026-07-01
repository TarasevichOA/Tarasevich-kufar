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
        // 1. Создаем объект библиотеки JavaFaker для генерации случайных реалистичных данных
        Faker faker = new Faker();
        // Генерируем случайный, но валидный по структуре email-адрес (например, "john.doe@gmail.com")
        String emiL = faker.internet().emailAddress();

        // 2. Формируем тело JSON-запроса (body) в виде обычной строки.
        // Передаем туда сгенерированный email, а поле "password" намеренно оставляем пустым ("")
        String body= "{\n" +
                "    \"login\": \""+ emiL +"\",\n" +
                "    \"password\": \"\"\n" +
                "}";

        // 3. Начинаем построение HTTP-запроса через RestAssured
        given()
                // Указываем заголовок запроса: сообщаем серверу, что отправляем данные в формате JSON и кодировке UTF-8
                .header("content-type", "application/json;charset=UTF-8")
                // Передаем созданную ранее JSON-строку в качестве тела запроса
                .body(body)
        .when()
                // Выполняем отправку HTTP-запроса методом POST на эндпоинт, сохраненный в константе LOGIN_URL
                .post(LOGIN_URL)
        .then()
                // Проверяем ответ от сервера (Assertion):
                // Ожидаем, что сервер вернет HTTP-статус 400 (Bad Request / Некорректный запрос)
                .statusCode(400)
                // Проверяем JSON-ответ: ищем в нем поле "label.text" и проверяем, что его значение
                // строго совпадает с ожидаемым текстом "Не заполнено обязательное поле"
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
