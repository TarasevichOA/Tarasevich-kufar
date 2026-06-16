package by.kufar;

import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebElement;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AuthFormTest extends BaseTest {
    private AuthFormPage authFormPage;
    private static final Logger logger = LogManager.getLogger(AuthFormTest.class);

    @BeforeEach
    public void setupAuthFormContext() {
        //HomePage homePage = new HomePage();
        homePage.clickCookies();
        homePage.clickButtonAuth();
        authFormPage = new AuthFormPage();
    }

    @DisplayName("Checking the registration form header with empty values")
    @Test
    public void checkTitle() {
        logger.info("The name of the \"Вход\" button is being checked.");
        Assertions.assertEquals("Вход", authFormPage.getTitleText());
    }

    @DisplayName("Validating a login form with empty values")
    @Test
    public void testEmptyFields() {
        authFormPage.setInputName("");
        authFormPage.setInputPassword("");
        authFormPage.setInputName("");
        logger.info("Check when User doesn't enter any value to Name and Password field.");

        Assertions.assertEquals("Заполните обязательное поле", authFormPage.getErrorMessageName());
        Assertions.assertEquals("Введите пароль", authFormPage.getErrorMessagePassword());
        logger.info("Errors are displayed for User.");
    }

    @DisplayName("Checking the login form for a non-existent login")
    @Test
    public void testUnknownProfile() {
        Faker faker = new Faker();
        String password = faker.internet().password();
        String email = "testtesttest@test.com";
        authFormPage.setInputName(email);
        logger.info("User inputs Email {}.", email);
        authFormPage.setInputPassword(password);
        logger.info("User inputs Password {}.", password);
        authFormPage.clickButtonSubmit();

        Assertions.assertEquals("Такого профиля не существует", authFormPage.getErrorMessageAuth());
    }

    @DisplayName("Checking the login form for a blocked profile")
    @Test
    public void testBlockProfile() {
        authFormPage.setInputName("test@test.com");
        authFormPage.setInputPassword("123456789");
        authFormPage.clickButtonSubmit();
        String errorMessageBlockProfile = authFormPage.getErrorMessageBlockProfile();

        Assertions.assertEquals("Профиль заблокирован и не может быть использован. Возможные причины блокировки" +
                " здесь", errorMessageBlockProfile);
        logger.info("User see Error {}.", errorMessageBlockProfile);
    }

    @DisplayName("Checking the login form with an empty name")
    @Test
    public void testCheckErrorEmail() {
        logger.info("Check when User doesn't enter any value to Name.");
        authFormPage.setInputName("");
        authFormPage.setInputPassword("123");

        Assertions.assertEquals("Заполните обязательное поле", authFormPage.getErrorMessageName());
    }

    @DisplayName("Checking the login form with an empty password")
    @Test
    public void testCheckErrorPassword() {
        logger.info("Check when User doesn't enter any value to Password.");
        authFormPage.setInputPassword("");
        authFormPage.setInputName("test@test.com");

        Assertions.assertEquals("Введите пароль", authFormPage.getErrorMessagePassword());
    }

    @DisplayName("Checking that the 'Forgot your password?' link is active")
    @Test
    public void testCheckLinkForgotPassword() {
        logger.info("Check that 'Forgot your password?' link is active.");
        WebElement link = authFormPage.getLinkForgotPassword();
        Assertions.assertTrue(link.isEnabled(), "Линка активна");
    }

    @DisplayName("Checking that the 'Forgot your password?' link is clickable")
    @Test
    public void testCheckLinkForgotPasswordClickable() {
        logger.info("Check that 'Forgot your password?' link is clickable.");
        WebElement link = authFormPage.getLinkForgotPassword();
        Assertions.assertTrue(link.isEnabled(), "Линка активна");
        authFormPage.clickLinkForgotPassword();
    }

    @DisplayName("Checking that you can go to the Registration tab")
    @Test
    public void testCheckLinkRegistration() {
        logger.info("Check that User go to the Registration tab");
        authFormPage.clickLinkRegistration();
    }

    @DisplayName("Checking button \"Close\"")
    @Test
    public void testCheckButtonClose() {
        authFormPage.clickButtonClose();
    }
}
