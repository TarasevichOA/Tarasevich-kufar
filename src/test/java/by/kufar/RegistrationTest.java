package by.kufar;


import by.kufar.driver.Driver;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RegistrationTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(RegistrationTest.class);
    private AuthFormPage authFormPage;
    private RegistrationPage registrationPage;
    HomePage homePage = new HomePage();
    Faker faker = new Faker();

    @BeforeEach
    public void setupRegistration() {
        homePage.clickCookies();
        homePage.clickButtonAuth();
        authFormPage = new AuthFormPage();
        authFormPage.clickLinkRegistration();
        registrationPage = new RegistrationPage();
    }

    @DisplayName("Checking the registration form with empty email addresses")
    @Test
    public void testErrorMessageEmail() {
        String email = "";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        //registrationPage.setInputRepeatPassword(password);

        String textErrorMessageEmail = registrationPage.getErrorMessageEmail();
        logger.info("The error for wrong e-mail in the registration form is equal {}", textErrorMessageEmail);
        Assertions.assertEquals("Заполните обязательное поле", textErrorMessageEmail);
    }

    @DisplayName("Checking email on the registration form when enter numbers")
    @Test
    public void testErrorMessageWrongEmail() {
        String email = faker.number().digits(3);
        String password = "";
        logger.info("User's email is equal {}", email);

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);

        String textErrorMessageWrongEmail = registrationPage.getErrorMessageWrongEmail();
        logger.info("The error for wrong e-mail (enter only numbers) in the registration form is equal {}", textErrorMessageWrongEmail);
        Assertions.assertEquals("Проверьте введенный email - неправильный формат", textErrorMessageWrongEmail);
    }

    @DisplayName("Checking the checkbox in the registration form")
    @Test
    public void testCheckboxUserAgreement() {
        // Генерация случайных валидных данных
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16, true, false, true);
        logger.info("User's email and password is equal {}", email, password);

        registrationPage.setInputEmail(email);
        registrationPage.setInputPassword(password);
        //registrationPage.setInputRepeatPassword(password);
        registrationPage.clickCheckboxUserAgreement();

        // Ждем, пока атрибут изменится на "true"
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        boolean isSelected = wait.until(d -> registrationPage.isCheckboxSelected());

        Assertions.assertTrue(isSelected, "Чекбокс должен быть выбран после клика");
        logger.info("The checkbox is checked in the registration form");
    }

    @Disabled("Отключен, так как поменялась логика")
    @DisplayName("Проверка формы регистрации для не валидного юзера")
    @Test
    public void testErrorMessageWrongRegistration() {
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

    @Disabled("Отключен, так как поменялась логика")
    @DisplayName("Проверка формы регистрации при не верном повторном пароле")
    @Test
    public void testErrorMessageRepeatPassword() {
        String email = "testtest@tut.by";
        String password = "";

        registrationPage.setInputEmail(email);
        registrationPage.setInputRepeatPassword("123");
        registrationPage.setInputPassword(password);

        Assertions.assertEquals("Пароли не совпадают. Введите пароль заново", registrationPage.getErrorMessageRepeatPassword());
    }

    @DisplayName("Checking the registration form if the password is incorrect")
    @Test
    public void testErrorMessagePassword() {
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

    @Disabled("Отключен, так как поменялась логика")
    @DisplayName("Проверка формы регистрации при не верных паролях")
    @Test
    public void testErrorMessagePasswords() {
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
