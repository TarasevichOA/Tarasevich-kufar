package by.kufar.driver;

import org.openqa.selenium.WebDriver;

/**
 * Класс-синглтон для управления экземплярами WebDriver.
 * Использует ThreadLocal для обеспечения потокобезопасности при параллельном тестировании.
 */
public class Driver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> browserNameThreadLocal = new ThreadLocal<>();

    private Driver() {
    }

    /**
     * Устанавливает имя браузера для текущего потока перед его инициализацией.
     * @param browserName название браузера (например, "chrome", "firefox")
     */
    public static void setBrowserName(String browserName) {
        browserNameThreadLocal.set(browserName);
    }

    /**
     * Возвращает экземпляр WebDriver для текущего потока.
     * Если драйвер еще не создан, инициализирует его (Ленивая инициализация).
     */
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

    /**
     * Завершает сессию браузера и полностью очищает память потока.
     * Обязательно вызывается после каждого теста во избежание утечек памяти.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();

        if (driver != null) {
            driver.quit();
        }

        driverThreadLocal.remove();
        browserNameThreadLocal.remove();

        WaitManager.unload();
    }
}
