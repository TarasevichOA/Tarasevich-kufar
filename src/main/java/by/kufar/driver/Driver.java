package by.kufar.driver;

import org.openqa.selenium.WebDriver;

public class Driver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            // Делегируем создание браузера нашей фабрике
            WebDriver driver = WebDriverFactory.createDriverInstance();
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
        WaitManager.unload();
    }
}


