package by.kufar;

import by.kufar.driver.Driver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    @BeforeEach
    public void setupAuth(TestInfo testInfo) {
        String testName = testInfo.getTestMethod().get().getName();
        ThreadContext.put("logFileName", testName);
        logger.info("Старт теста: {}", testName);

        // Считываем текущий браузер из системы, чтобы передать его в отчет Allure
        String currentBrowser = System.getProperty("browser", "chrome");
        Allure.parameter("Браузер", currentBrowser);

        HomePage homePage = new HomePage();
        homePage.open();
        // homePage.clickCookies();
    }

    @AfterEach
    public void closeDriver() {
        logger.info("Тест завершен. Закрываю браузер.");
        Driver.quitDriver();
        ThreadContext.clearAll();
    }
}
