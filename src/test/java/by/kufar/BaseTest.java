package by.kufar;

import by.kufar.driver.Driver;
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
            // Динамически берём путь к allure-results из системы (учитывает target)
            String resultsDirPath = System.getProperty("allure.results.directory", "target/allure-results");
            File allureResultsDir = new File(resultsDirPath);

            if (!allureResultsDir.exists()) {
                allureResultsDir.mkdirs();
            }

            Properties properties = new Properties();
            properties.setProperty("Browser", "Cross-Browser (Chrome, Firefox, Edge)");
            properties.setProperty("Environment", "Jenkins CI");
            properties.setProperty("Threads", "3 (Fixed Strategy)");
            properties.setProperty("URL", "https://kufar.by");

            // Записываем файл строго в целевую папку результатов
            FileOutputStream fos = new FileOutputStream(new File(allureResultsDir, "environment.properties"));
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

        //String mavenBrowser = System.getProperty("browser", "chrome");

        // 2. Явно передаем имя браузера в ThreadLocal для текущего потока тестов
        //Driver.setBrowserName(mavenBrowser);

        // 3. Инициализируем глобальное поле (убрали слово HomePage в начале строки!)
        homePage = new HomePage();
        //homePage.open();
    }

    @AfterEach
    public void closeDriver() {
        logger.info("Тест завершен. Закрываю браузер.");
        Driver.quitDriver();
        ThreadContext.clearAll();
    }
}
