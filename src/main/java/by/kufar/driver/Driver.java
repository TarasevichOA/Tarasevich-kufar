package by.kufar.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
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
