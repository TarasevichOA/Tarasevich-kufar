package by.kufar;

import by.kufar.driver.Driver;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebElement;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AuthFormTest extends BaseTest {
    private AuthFormPage authFormPage;
    private static final Logger logger = LogManager.getLogger(AuthFormTest.class);

    @BeforeEach
    public void setupAuthFormContext(TestInfo testInfo) {
        String browser = testInfo.getDisplayName()
                .replaceAll(".*:\\s*", "")
                .trim();

        if (browser.isEmpty() || browser.contains(" ") || browser.contains("()")) {
            browser = System.getProperty("browser", "chrome");
        }

        Driver.setBrowserName(browser);

        homePage.open();

        homePage.clickCookies();
        homePage.clickButtonAuth();
        authFormPage = new AuthFormPage();
    }

    @ParameterizedTest(name = "Checking the registration form header with empty values in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void checkTitle(String browser) {
        logger.info("Браузер [{}]: The name of the \"Вход\" button is being checked.", browser);
        Assertions.assertEquals("Вход", authFormPage.getTitleText());
    }

    @ParameterizedTest(name = "Validating a login form with empty values in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testEmptyFields(String browser) {
        authFormPage.setInputName("");
        authFormPage.setInputPassword("");
        logger.info("Браузер [{}]: Check when User doesn't enter any value to Name and Password field.", browser);

        Assertions.assertEquals("Заполните обязательное поле", authFormPage.getErrorMessageName());
        Assertions.assertEquals("Введите пароль", authFormPage.getErrorMessagePassword());
        logger.info("Браузер [{}]: Errors are displayed for User.", browser);
    }

    @ParameterizedTest(name = "Checking the login form for a non-existent login in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testUnknownProfile(String browser) {
        Faker faker = new Faker();
        String password = faker.internet().password();
        String email = "testtesttest@test.com";
        authFormPage.setInputName(email);
        logger.info("Браузер [{}]: User inputs Email {}.", browser, email);
        authFormPage.setInputPassword(password);
        logger.info("Браузер [{}]: User inputs Password {}.", browser, password);
        authFormPage.clickButtonSubmit();

        Assertions.assertEquals("Такого профиля не существует", authFormPage.getErrorMessageAuth());
    }

    @ParameterizedTest(name = "Checking the login form for a blocked profile in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testBlockProfile(String browser) {
        authFormPage.setInputName("test@test.com");
        authFormPage.setInputPassword("123456789");
        authFormPage.clickButtonSubmit();
        String errorMessageBlockProfile = authFormPage.getErrorMessageBlockProfile();

        Assertions.assertEquals("Профиль заблокирован и не может быть использован. Возможные причины блокировки" +
                " здесь", errorMessageBlockProfile);
        logger.info("Браузер [{}]: User see Error {}.", browser, errorMessageBlockProfile);
    }

    @ParameterizedTest(name = "Checking the login form with an empty name in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckErrorEmail(String browser) {
        logger.info("Браузер [{}]: Check when User doesn't enter any value to Name.", browser);
        authFormPage.setInputName("");
        authFormPage.setInputPassword("123");

        Assertions.assertEquals("Заполните обязательное поле", authFormPage.getErrorMessageName());
    }

    @ParameterizedTest(name = "Checking the login form with an empty password in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckErrorPassword(String browser) {
        logger.info("Браузер [{}]: Check when User doesn't enter any value to Password.", browser);
        authFormPage.setInputPassword("");
        authFormPage.setInputName("test@test.com");

        Assertions.assertEquals("Введите пароль", authFormPage.getErrorMessagePassword());
    }

    @ParameterizedTest(name = "Checking that the 'Forgot your password?' link is active in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckLinkForgotPassword(String browser) {
        logger.info("Браузер [{}]: Check that 'Forgot your password?' link is active.", browser);
        WebElement link = authFormPage.getLinkForgotPassword();
        Assertions.assertTrue(link.isEnabled(), "Линка активна");
    }

    @ParameterizedTest(name = "Checking that the 'Forgot your password?' link is clickable in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckLinkForgotPasswordClickable(String browser) {
        logger.info("Браузер [{}]: Check that 'Forgot your password?' link is clickable.", browser);
        WebElement link = authFormPage.getLinkForgotPassword();
        Assertions.assertTrue(link.isEnabled(), "Линка активна");
        authFormPage.clickLinkForgotPassword();
    }

    @ParameterizedTest(name = "Checking that you can go to the Registration tab in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckLinkRegistration(String browser) {
        logger.info("Браузер [{}]: Check that User go to the Registration tab", browser);
        authFormPage.clickLinkRegistration();
    }

    @ParameterizedTest(name = "Checking button \"Close\" in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckButtonClose(String browser) {
        logger.info("Браузер [{}]: Checking button \"Close\"", browser);
        authFormPage.clickButtonClose();
    }
}
