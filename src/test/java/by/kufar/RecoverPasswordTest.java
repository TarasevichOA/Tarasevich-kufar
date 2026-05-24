package by.kufar;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RecoverPasswordTest {
    private WebDriver driver;
    private RecoverPasswordPage recoverPasswordPage;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        recoverPasswordPage = new RecoverPasswordPage();
        recoverPasswordPage.open();
        recoverPasswordPage.clickCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DisplayName("Проверка заголовка в форме регистрации")
    @Test
    public void checkTitle() {
        Assertions.assertEquals("Восстановление пароля", recoverPasswordPage.getTitleText());
    }

    @DisplayName("Проверка формы регистрации с пустыми значением")
    @Test
    public void checkUserEmptyData() {
        Assertions.assertEquals("Введите e-mail", recoverPasswordPage.getExtendedDescriptionError());
    }

    @DisplayName("Проверка формы регистрации с неверным значением")
    @Test
    public void testRE005() {
        recoverPasswordPage.setInputEmail("375");
        recoverPasswordPage.clickButtonAuth();

        Assertions.assertEquals("Проверьте введенный e-mail - неправильный формат", recoverPasswordPage.getExtendedErrorWrongEmail());
    }

    @Test
    @DisplayName("Проверка, что кнопка изначально заблокирована")
    void testButtonStateDisabled() {
        WebElement button = recoverPasswordPage.getAuthButton();

        // 1. Проверяем, что кнопка изначально заблокирована
        // Используем assertFalse, так как isEnabled() вернет false для задизейбленной кнопки
        Assertions.assertFalse(button.isEnabled(), "Кнопка ДОЛЖНА БЫТЬ заблокирована при пустом вводе");
    }

    @Test
    @DisplayName("Проверка, что кнопка изначально заблокирована")
    void testButtonStateEnabled() {
        WebElement button = recoverPasswordPage.getAuthButton();

        recoverPasswordPage.setInputEmail("test@example.com");
        Assertions.assertTrue(button.isEnabled(), "Кнопка ДОЛЖНА СТАТЬ активной после ввода email");
    }
}
