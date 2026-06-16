package by.kufar;

import by.kufar.driver.Driver;
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
public class RecoverPasswordTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(RecoverPasswordTest.class);
    private RecoverPasswordPage recoverPasswordPage;

    @BeforeEach
    public void setupRecoverPasswordContext(TestInfo testInfo) {
        recoverPasswordPage = new RecoverPasswordPage();

        // ИСПРАВЛЕНО: Добавлен двойной слэш \\s для корректного экранирования в Java
        String browser = testInfo.getDisplayName()
                .replaceAll(".*:\\s*", "")
                .trim();

        // Если имя не распарсилось (например, при запуске обычного @Test), берем дефолтный из Maven
        if (browser.isEmpty() || browser.contains(" ") || browser.contains("()")) {
            browser = System.getProperty("browser", "chrome");
        }

        // Настраиваем поток и открываем нужный браузер
        Driver.setBrowserName(browser);
        recoverPasswordPage.open();
        recoverPasswordPage.clickCookies();
    }

    @DisplayName("Checking the title on the recover Password form")
    @ParameterizedTest(name = "Проверка заголовка восстановления пароля в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void checkTitle(String browser) {
        String textTitleOnRecoverPasswordPage = recoverPasswordPage.getTitleText();
        Assertions.assertEquals("Восстановление пароля", textTitleOnRecoverPasswordPage);
        logger.info("The title in the registration form is equal {}", textTitleOnRecoverPasswordPage);
    }

    @DisplayName("Checking the recover Password form with empty values")
    @ParameterizedTest(name = "Проверка пустых полей формы восстановления в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void checkUserEmptyData(String browser) {
        String textErrorUserEmail = recoverPasswordPage.getExtendedDescriptionError();
        Assertions.assertEquals("Введите e-mail", textErrorUserEmail);
        logger.info("The error for empty e-mail in the registration form is equal {}", textErrorUserEmail);
    }

    @DisplayName("Checking the recover Password form with an invalid value")
    @ParameterizedTest(name = "Проверка некорректного email в форме восстановления в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void checkFormWithWrongData(String browser) {
        recoverPasswordPage.setInputEmail("375");
        recoverPasswordPage.clickButtonAuth();
        String textErrorInvalidUserEmail = recoverPasswordPage.getExtendedErrorWrongEmail();

        Assertions.assertEquals("Проверьте введенный e-mail - неправильный формат", textErrorInvalidUserEmail);
        logger.info("The error for wrong e-mail in the registration form is equal {}", textErrorInvalidUserEmail);
    }

    @DisplayName("Checking that the button is initially locked")
    @ParameterizedTest(name = "Проверка блокировки кнопки отправки в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    void testButtonStateDisabled(String browser) {
        WebElement button = recoverPasswordPage.getAuthButton();

        // 1. Проверяем, что кнопка изначально заблокирована
        // Используем assertFalse, так как isEnabled() вернет false для задизейбленной кнопки
        Assertions.assertFalse(button.isEnabled(), "Кнопка ДОЛЖНА БЫТЬ заблокирована при пустом вводе");
        logger.info("The button is initially locked");
    }

    @DisplayName("Checking that the button is active after entering email")
    @ParameterizedTest(name = "Проверка разблокировки кнопки отправки в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    void testButtonStateEnabled(String browser) {
        WebElement button = recoverPasswordPage.getAuthButton();

        recoverPasswordPage.setInputEmail("test@example.com");
        Assertions.assertTrue(button.isEnabled(), "Кнопка ДОЛЖНА СТАТЬ активной после ввода email");
        logger.info("The button is active after entering email");
    }
}
