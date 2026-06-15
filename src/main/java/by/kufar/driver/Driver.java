package by.kufar.driver;

import org.openqa.selenium.WebDriver;

public class Driver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> browserNameThreadLocal = new ThreadLocal<>();

    private Driver() {}

    public static void setBrowserName(String browserName) {
        browserNameThreadLocal.set(browserName);
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            WebDriver driver;
            if (browserNameThreadLocal.get() != null) {
                driver = WebDriverFactory.createDriverInstance(browserNameThreadLocal.get());
            } else {
                driver = WebDriverFactory.createDriverInstance();
            }
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
        }
        driverThreadLocal.remove();
        browserNameThreadLocal.remove();

        // Обязательно очищаем WebDriverWait для этого потока!
        WaitManager.unload();
    }
}


