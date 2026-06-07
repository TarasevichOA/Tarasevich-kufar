package by.kufar.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private Driver() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            // 1. Создаем настройки для браузера Chrome
            ChromeOptions options = new ChromeOptions();

            // 2. Жестко задаем размер окна, чтобы верстка в Jenkins не схлопывалась
            options.addArguments("--window-size=1920,1080");

            // 3. Запускаем в headless-режиме (без графического окна для CI/CD)
            options.addArguments("--headless=new");

            // 4. Дополнительные флаги для стабильности в корпоративных сетях и Docker/Jenkins
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            // Инициализируем драйвер с нашими настройками
            WebDriver driver = new ChromeDriver(options);

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
