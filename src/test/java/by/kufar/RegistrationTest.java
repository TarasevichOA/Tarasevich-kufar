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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RegistrationTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(RegistrationTest.class);
    private AuthFormPage authFormPage;
    private RegistrationPage registrationPage;
    private final Faker faker = new Faker();

    @BeforeEach
    public void setupRegistrationContext(TestInfo testInfo) {
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
        authFormPage.clickLinkRegistration();
        registrationPage = new RegistrationPage();
    }

    @ParameterizedTest(name = "Checking the registration form with empty fields in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testErrorMessageEmail(String browser) {
        String email = "";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        //registrationPage.setInputRepeatPassword(password);

        String textErrorMessageEmail = registrationPage.getErrorMessageEmail();
        logger.info("Браузер [{}]: The error for wrong e-mail in the registration form is equal {}", browser, textErrorMessageEmail);
        Assertions.assertEquals("Заполните обязательное поле", textErrorMessageEmail);
    }

    @ParameterizedTest(name = "Checking email on the registration form when enter numbers in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testErrorMessageWrongEmail(String browser) {
        String email = faker.number().digits(3);
        String password = "";
        logger.info("User's email is equal {}", email);

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);

        String textErrorMessageWrongEmail = registrationPage.getErrorMessageWrongEmail();
        logger.info("Браузер [{}]: The error for wrong e-mail (enter only numbers) in the registration form is equal {}", browser, textErrorMessageWrongEmail);
        Assertions.assertEquals("Проверьте введенный email - неправильный формат", textErrorMessageWrongEmail);
    }

    @ParameterizedTest(name = "Checking the checkbox in the registration form in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckboxUserAgreement(String browser) {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16, true, false, true);
        logger.info("User's email and password is equal {}", email, password);

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        //registrationPage.setInputRepeatPassword(password);
        registrationPage.clickCheckboxUserAgreement();

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        boolean isSelected = wait.until(d -> registrationPage.isCheckboxSelected());

        Assertions.assertTrue(isSelected, "Чекбокс должен быть выбран после клика");
        logger.info("The checkbox is checked in the registration form");
    }

    @Disabled("Disabled due to changed logic")
    @ParameterizedTest(name = "Проверка формы регистрации для не валидного юзера in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testErrorMessageWrongRegistration(String browser) {
        String email = "testtest@tut.by";
        String password = "13123Dhgfdy3142";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        //registrationPage.setInputRepeatPassword(password);
        registrationPage.clickCaptcha();
        registrationPage.clickCheckboxUserAgreement();
        registrationPage.clickButtonSubmit();

        Assertions.assertEquals("Произошла ошибка при активации профиля. Запросите новую ссылку, чтобы завершить регистрацию", registrationPage.getErrorMessageWrongRegistration());
    }

    @Disabled("Disabled due to changed logic")
    @ParameterizedTest(name = "Проверка формы регистрации при не верном повторном пароле in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testErrorMessageRepeatPassword(String browser) {
        String email = "testtest@tut.by";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputRepeatPassword("123");
        registrationPage.setInputPassword(password);

        Assertions.assertEquals("Пароли не совпадают. Введите пароль заново", registrationPage.getErrorMessageRepeatPassword());
    }

    @ParameterizedTest(name = "Checking the registration form if the password is incorrect in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testErrorMessagePassword(String browser) {
        // Генерация случайного email и пароля из 3 цифр
        String email = faker.internet().emailAddress();
        String password = faker.number().digits(3);
        logger.info("User's email and password is equal {}", email, password);

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        //registrationPage.setInputRepeatPassword(password);

        String textErrorMessagePassword = registrationPage.getErrorMessagePassword();
        logger.info("The error for wrong password in the registration form is equal {}", textErrorMessagePassword);
        Assertions.assertTrue(textErrorMessagePassword.startsWith("Пароль должен содержать:"),
                "Текст ошибки должен начинаться с верной фразы, а пришло: " + textErrorMessagePassword);
    }

    @Disabled("Disabled due to changed logic")
    @ParameterizedTest(name = "Проверка формы регистрации при не верных паролях in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testErrorMessagePasswords(String browser) {
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
