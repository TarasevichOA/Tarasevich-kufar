package by.kufar;

import by.kufar.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
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

    @BeforeEach
    public void setupAuth(TestInfo testInfo) {
        String testName = testInfo.getTestMethod().get().getName();
        ThreadContext.put("logFileName", testName);
        logger.info("Старт теста: {}", testName);

        homePage = new HomePage();
        //homePage.open();
        try {
            String resultsDir = System.getProperty("allure.results.directory", "target/allure-results");
            File dir = new File(resultsDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Properties props = new Properties();
            props.setProperty("Browser", "Cross-Browser (Chrome, Firefox, Edge)");
            props.setProperty("Environment", "Jenkins CI");
            props.setProperty("Threads", "3 (Fixed Strategy)");
            props.setProperty("URL", "https://kufar.by");

            FileOutputStream fos = new FileOutputStream(new File(dir, "environment.properties"));
            props.store(fos, "Allure Environment");
            fos.close();
        } catch (IOException e) {
            logger.error("Не удалось записать файл окружения Allure: {}", e.getMessage());
        }
    }

    @AfterEach
    public void closeDriver() {
        logger.info("Тест завершен. Закрываю браузер.");
        Driver.quitDriver();
        ThreadContext.clearAll();
    }
}
