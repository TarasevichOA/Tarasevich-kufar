package by.kufar;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationTest extends BaseTest {
    private AuthFormPage authFormPage;
    private RegistrationPage registrationPage;

    @BeforeEach
    public void setupRegistration() {
        homePage.clickCookies();
        homePage.clickButtonAuth();
        authFormPage = new AuthFormPage(driver);
        authFormPage.clickLinkRegistration();
        registrationPage = new RegistrationPage(driver);
    }

    @DisplayName("Проверка формы регистрации с пустыми email")
    @Test
    public void testErrorMessageEmail(){
        String email = "";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        registrationPage.setInputRepeatPassword(password);

        String actualButtonCheckText = registrationPage.getErrorMessageEmail();
        Assertions.assertEquals("Заполните обязательное поле", actualButtonCheckText);
    }

    @DisplayName("Проверка формы регистрации со значением в виде цифр")
    @Test
    public void testErrorMessageWrongEmail(){
        String email = "123";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);

        String actualButtonCheckText = registrationPage.getErrorMessageWrongEmail();
        Assertions.assertEquals("Проверьте введенный email - неправильный формат", actualButtonCheckText);
    }

    @DisplayName("Проверка чекбокса в форме регистрации")
    @Test
    public void testCheckboxUserAgreement(){
        String email = "testtest@tut.by";
        String password = "13123Dhgfdy3142";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        registrationPage.setInputRepeatPassword(password);

        registrationPage.clickCheckboxUserAgreement();

        // Ждем, пока атрибут изменится на "true"
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isSelected = wait.until(d -> registrationPage.isCheckboxSelected());

        Assertions.assertTrue(isSelected, "Чекбокс должен быть выбран после клика");
    }

    @DisplayName("Проверка формы регистрации для не валидного юзера")
    @Test
    public void testErrorMessageWrongRegistration(){
        String email = "testtest@tut.by";
        String password = "13123Dhgfdy3142";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        registrationPage.setInputRepeatPassword(password);
        registrationPage.clickCaptcha();
        registrationPage.clickCheckboxUserAgreement();
        registrationPage.clickButtonSubmit();

        Assertions.assertEquals("Произошла ошибка при активации профиля. Запросите новую ссылку, чтобы завершить регистрацию", registrationPage.getErrorMessageWrongRegistration());
    }

    @DisplayName("Проверка формы регистрации при не верном повторном пароле")
    @Test
    public void testErrorMessageRepeatPassword(){
        String email = "testtest@tut.by";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputRepeatPassword("123");
        registrationPage.setInputPassword(password);

        Assertions.assertEquals("Пароли не совпадают. Введите пароль заново", registrationPage.getErrorMessageRepeatPassword());
    }

    @DisplayName("Проверка формы регистрации при не верном пароле")
    @Test
    public void testErrorMessagePassword(){
        String email = "testtest@tut.by";
        String password = "123";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        registrationPage.setInputRepeatPassword(password);

        String actualError = registrationPage.getErrorMessagePassword();
        Assertions.assertTrue(actualError.startsWith("Пароль должен содержать:"),
                "Текст ошибки должен начинаться с верной фразы, а пришло: " + actualError);
    }

    @DisplayName("Проверка формы регистрации при не верных паролях")
    @Test
    public void testErrorMessagePasswords(){
        String email = "testtest@tut.by";
        String password = "123";

        registrationPage.setInputRepeatPassword("password");
        registrationPage.setInputPassword(password);
        registrationPage.setInputEmail(email);

        String actualError = registrationPage.getErrorMessagePassword();
        Assertions.assertTrue(actualError.startsWith("Пароль должен содержать:"),
                "Текст ошибки должен начинаться с верной фразы, а пришло: " + actualError);
        Assertions.assertEquals("Пароли не совпадают. Введите пароль заново", registrationPage.getErrorMessageRepeatPassword());
    }
}
