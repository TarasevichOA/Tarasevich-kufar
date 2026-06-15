package by.kufar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.WebElement;


@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RecoverPasswordTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(RecoverPasswordTest.class);
    private RecoverPasswordPage recoverPasswordPage;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        String mavenBrowser = System.getProperty("browser", "chrome");
        startBrowserContext(mavenBrowser);
        String testName = testInfo.getTestMethod().get().getName();
        ThreadContext.put("logFileName", testName);

        recoverPasswordPage = new RecoverPasswordPage();
        recoverPasswordPage.open();
        recoverPasswordPage.clickCookies();
    }

    @DisplayName("Checking the title on the recover Password form")
    @Test
    public void checkTitle() {
        String textTitleOnRecoverPasswordPage = recoverPasswordPage.getTitleText();
        Assertions.assertEquals("Восстановление пароля", textTitleOnRecoverPasswordPage);
        logger.info("The title in the registration form is equal {}", textTitleOnRecoverPasswordPage);
    }

    @DisplayName("Checking the recover Password form with empty values")
    @Test
    public void checkUserEmptyData() {
        String textErrorUserEmail = recoverPasswordPage.getExtendedDescriptionError();
        Assertions.assertEquals("Введите e-mail", textErrorUserEmail);
        logger.info("The error for empty e-mail in the registration form is equal {}", textErrorUserEmail);
    }

    @DisplayName("Checking the recover Password form with an invalid value")
    @Test
    public void checkFormWithWrongData() {
        recoverPasswordPage.setInputEmail("375");
        recoverPasswordPage.clickButtonAuth();
        String textErrorInvalidUserEmail = recoverPasswordPage.getExtendedErrorWrongEmail();

        Assertions.assertEquals("Проверьте введенный e-mail - неправильный формат", textErrorInvalidUserEmail);
        logger.info("The error for wrong e-mail in the registration form is equal {}", textErrorInvalidUserEmail);
    }

    @DisplayName("Checking that the button is initially locked")
    @Test
    void testButtonStateDisabled() {
        WebElement button = recoverPasswordPage.getAuthButton();

        // 1. Проверяем, что кнопка изначально заблокирована
        // Используем assertFalse, так как isEnabled() вернет false для задизейбленной кнопки
        Assertions.assertFalse(button.isEnabled(), "Кнопка ДОЛЖНА БЫТЬ заблокирована при пустом вводе");
        logger.info("The button is initially locked");
    }

    @DisplayName("Checking that the button is active after entering email")
    @Test
    void testButtonStateEnabled() {
        WebElement button = recoverPasswordPage.getAuthButton();

        recoverPasswordPage.setInputEmail("test@example.com");
        Assertions.assertTrue(button.isEnabled(), "Кнопка ДОЛЖНА СТАТЬ активной после ввода email");
        logger.info("The button is active after entering email");
    }
}
