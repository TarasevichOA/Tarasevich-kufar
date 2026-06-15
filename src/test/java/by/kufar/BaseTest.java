package by.kufar;

import by.kufar.driver.Driver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected HomePage homePage;

    @BeforeAll
    public static void createAllureEnvironmentFile() {
        try {
            File allureResultsDir = new File("target/allure-results");
            if (!allureResultsDir.exists()) {
                allureResultsDir.mkdirs();
            }

            Properties properties = new Properties();
            properties.setProperty("Browser", "Cross-Browser (JUnit5)");
            properties.setProperty("Environment", "Jenkins CI");
            properties.setProperty("URL", "https://kufar.by");

            FileOutputStream fos = new FileOutputStream("target/allure-results/environment.properties");
            properties.store(fos, "Allure Environment Properties");
            fos.close();
        } catch (IOException e) {
            System.err.println("Не удалось создать файл environment.properties: " + e.getMessage());
        }
    }

    @BeforeEach
    public void setupAuth(TestInfo testInfo) {
        String testName = testInfo.getTestMethod().get().getName();
        ThreadContext.put("logFileName", testName);
        logger.info("Старт теста: {}", testName);

        // Инициализируем объект страницы заранее
        homePage = new HomePage();
    }

    /**
     * Вспомогательный метод для старта контекста внутри теста.
     * Сюда мы передаем имя браузера (из параметров JUnit 5 или дефолтное).
     */
    protected void startBrowserContext(String browserName) {
        // 1. Сохраняем имя браузера в ThreadLocal для фабрики
        Driver.setBrowserName(browserName);

        // 2. Логируем в Allure
        Allure.parameter("Браузер", browserName);
        logger.info("Инициализация браузера: {}", browserName);

        // 3. Открываем страницу (здесь сработает Driver.getDriver() с нужным браузером)
        homePage.open();
        // homePage.clickCookies();
    }

    @AfterEach
    public void closeDriver() {
        logger.info("Тест завершен. Закрываю браузер.");
        // quitDriver() закроет браузер, очистит ThreadLocal и вызовет WaitManager.unload()
        Driver.quitDriver();
        ThreadContext.clearAll();
    }
}

