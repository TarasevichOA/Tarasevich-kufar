package by.kufar.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {

    // Дефолтный метод (вызывается вашим классом Driver по умолчанию)
    public static WebDriver createDriverInstance() {
        // Читаем из системных свойств, если ничего другого не передано
        String browser = System.getProperty("browser", "chrome").toLowerCase().trim();
        return createDriverInstance(browser);
    }

    // НОВЫЙ МЕТОД: принимает имя браузера напрямую из теста/ThreadLocal
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
                edgeOptions.addArguments("--window-size=1920,1080");
                if (isHeadless) {
                    edgeOptions.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
                    edgeOptions.addArguments("--remote-allow-origins=*");
                    // Замените старую строку с --user-data-dir=C:\\Windows\\Temp\\EdgeProfile на эту:
                    edgeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "EdgeProfile_" + Thread.currentThread().getId());

                    edgeOptions.addArguments("--disable-extensions");
                }
                return new EdgeDriver(edgeOptions);

            case "chrome":
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--window-size=1920,1080");
                if (isHeadless) {
                    chromeOptions.addArguments("--headless", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    // Замените старую строку с --user-data-dir=C:\\Windows\\Temp\\ChromeProfile на эту:
                    chromeOptions.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + "ChromeProfile_" + Thread.currentThread().getId());

                    chromeOptions.addArguments("--disable-extensions");
                }
                return new ChromeDriver(chromeOptions);
        }
    }
}
