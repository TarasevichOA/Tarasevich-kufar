package by.kufar.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Фабрика для инициализации и настройки экземпляров WebDriver.
 * Поддерживает запуск различных браузеров и работу в фоновом (headless) режиме через системные флаги.
 */

/**
 * Оптимизированная фабрика для инициализации WebDriver.
 * Логика настройки Chromium-браузеров (Chrome, Edge) вынесена в отдельный метод.
 */
public class WebDriverFactory {

    /**
     * Создает экземпляр драйвера на основе системной переменной "browser".
     */
    public static WebDriver createDriverInstance() {
        String browser = System.getProperty("browser", "chrome").toLowerCase().trim();
        return createDriverInstance(browser);
    }

    /**
     * Создает экземпляр драйвера для конкретного указанного браузера.
     */
    public static WebDriver createDriverInstance(String browser) {
        browser = browser.toLowerCase().trim();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));

        switch (browser) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--window-size=1920,1080");
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                configureChromiumOptions(edgeOptions, isHeadless);
                return new EdgeDriver(edgeOptions);

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                configureChromiumOptions(chromeOptions, isHeadless);
                return new ChromeDriver(chromeOptions);
        }
    }

    /**
     * Вспомогательный приватный метод для настройки Chromium-совместимых браузеров (Chrome и Edge).
     * Избавляет от дублирования кода и упрощает поддержку флагов.
     *
     * @param options    объект настроек ChromeOptions или EdgeOptions
     * @param isHeadless флаг включения фонового режима
     */
    private static void configureChromiumOptions(ChromiumOptions<?> options, boolean isHeadless) {
        // Устанавливаем базовое разрешение для всех Chromium браузеров
        options.addArguments("--window-size=1920,1080");

        if (isHeadless) {
            options.addArguments(
                    "--headless=new",
                    "--disable-blink-features=AutomationControlled",
                    "--disable-gpu",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-extensions"
            );
        }
    }
}
