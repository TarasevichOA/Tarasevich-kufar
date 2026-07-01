package by.kufar.driver;

import org.openqa.selenium.WebDriver;

/**
 * Класс-синглтон для управления экземплярами WebDriver.
 * Использует ThreadLocal для обеспечения потокобезопасности при параллельном тестировании.
 */
public class Driver {

    // Потокобезопасный контейнер для хранения экземпляра драйвера (у каждого потока свой браузер)
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Потокобезопасный контейнер для хранения имени браузера, который нужно запустить в текущем потоке
    private static final ThreadLocal<String> browserNameThreadLocal = new ThreadLocal<>();

    // Приватный конструктор запрещает создание объектов этого класса через 'new'
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
        // Проверяем, запущен ли уже браузер в текущем потоке
        if (driverThreadLocal.get() == null) {
            WebDriver driver;

            // Если имя браузера было предварительно задано, создаем конкретный браузер
            if (browserNameThreadLocal.get() != null) {
                driver = WebDriverFactory.createDriverInstance(browserNameThreadLocal.get());
            } else {
                // Если имя не задано, создаем браузер по умолчанию
                driver = WebDriverFactory.createDriverInstance();
            }

            // Сохраняем созданный драйвер в контекст текущего потока
            driverThreadLocal.set(driver);
        }
        // Возвращаем существующий драйвер для этого потока
        return driverThreadLocal.get();
    }

    /**
     * Завершает сессию браузера и полностью очищает память потока.
     * Обязательно вызывается после каждого теста во избежание утечек памяти.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();

        // Если браузер был запущен, закрываем его окно и завершаем процесс
        if (driver != null) {
            driver.quit();
        }

        // Удаляем объекты из ThreadLocal, чтобы избежать утечек памяти (Memory Leaks) при переиспользовании потоков
        driverThreadLocal.remove();
        browserNameThreadLocal.remove();

        // Очищаем кастомный менеджер ожиданий, связанный с этим потоком
        WaitManager.unload();
    }
}
