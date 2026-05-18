package by.kufar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class AuthFormTest extends BaseTest {
    private AuthFormPage authFormPage;

    @BeforeEach
    public void setupAuth() {
        homePage.clickButtonAuth();
        homePage.clickCookies();
        authFormPage = new AuthFormPage(driver);
    }

    @DisplayName("Проверка заголовка в форме регистрации с пустыми значением")
    @Test
    public void checkTitle() {
        Assertions.assertEquals("Вход", authFormPage.getTitleText());
    }

    @DisplayName("Проверка формы логина с пустыми значениями")
    @Test
    public void testEmptyFields() {
        authFormPage.setInputName("");
        authFormPage.setInputPassword("");
        authFormPage.setInputName("");

        Assertions.assertEquals("Заполните обязательное поле", authFormPage.getErrorMessageName());
        Assertions.assertEquals("Введите пароль", authFormPage.getErrorMessagePassword());
    }

    @DisplayName("Проверка формы логина для не существующего логина")
    @Test
    public void testUnknownProfile(){
        authFormPage.setInputName("testtesttest@test.com");
        authFormPage.setInputPassword("123456789");
        authFormPage.clickButtonSubmit();

        Assertions.assertEquals("Такого профиля не существует", authFormPage.getErrorMessageAuth());
    }

    @DisplayName("Проверка формы логина для заблокированного профиля")
    @Test
    public void testBlockProfile(){
        authFormPage.setInputName("test@test.com");
        authFormPage.setInputPassword("123456789");
        authFormPage.clickButtonSubmit();

        Assertions.assertEquals("Профиль заблокирован и не может быть использован. Возможные причины блокировки" +
                " здесь", authFormPage.getErrorMessageBlockProfile());
    }

    @DisplayName("Проверка формы логина с пустым именем")
    @Test
    public void testCheckErrorEmail() {
        authFormPage.setInputName("");
        authFormPage.setInputPassword("123");

        Assertions.assertEquals("Заполните обязательное поле", authFormPage.getErrorMessageName());
    }

    @DisplayName("Проверка формы логина с пустыми паролем")
    @Test
    public void testCheckErrorPassword(){
        authFormPage.setInputPassword("");
        authFormPage.setInputName("test@test.com");

        Assertions.assertEquals("Введите пароль", authFormPage.getErrorMessagePassword());
    }

    @DisplayName("Проверка, что линка 'Забыли пароль?' активна")
    @Test
    public void testCheckLinkForgotPassword(){
        WebElement link = authFormPage.getLinkForgotPassword();
        Assertions.assertTrue(link.isEnabled(), "Линка активна");
    }

    @DisplayName("Проверка, что линка 'Забыли пароль?' кликабельна")
    @Test
    public void testCheckLinkForgotPasswordClickable(){
        WebElement link = authFormPage.getLinkForgotPassword();
        Assertions.assertTrue(link.isEnabled(), "Линка активна");
        authFormPage.clickLinkForgotPassword();
    }

    @DisplayName("Проверка, что можно перейти на вкладку Регистрации")
    @Test
    public void testCheckLinkRegistration(){
        authFormPage.clickLinkRegistration();
    }

    @DisplayName("Проверка кнопки Close")
    @Test
    public void testCheckButtonClose(){
        authFormPage.clickButtonClose();
    }
}
