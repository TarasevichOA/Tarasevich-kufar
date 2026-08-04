package by.kufar.driver;

import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Класс-синглтон для управления явными ожиданиями (WebDriverWait).
 * Работает через ThreadLocal, привязывая свой экземпляр ожидания к конкретному потоку теста.
 */
public class WaitManager {
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();

    private static final long TIMEOUT_SECONDS = 20;

    private WaitManager() {
    }

    /**
     * Возвращает экземпляр WebDriverWait для текущего потока.
     * Если объект еще не создан, инициализирует его (Ленивая инициализация).
     */
    public static WebDriverWait getWait() {
        if (waitThreadLocal.get() == null) {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(TIMEOUT_SECONDS));
            waitThreadLocal.set(wait);
        }
        return waitThreadLocal.get();
    }

    /**
     * Очищает ссылку на WebDriverWait из памяти текущего потока.
     * Автоматически вызывается внутри Driver.quitDriver() в конце каждого теста.
     */
    public static void unload() {
        waitThreadLocal.remove();
    }
}
