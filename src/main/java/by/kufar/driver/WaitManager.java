package by.kufar.driver;

import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Класс-синглтон для управления явными ожиданиями (WebDriverWait).
 * Работает через ThreadLocal, привязывая свой экземпляр ожидания к конкретному потоку теста.
 */
public class WaitManager {

    // Потокобезопасный контейнер для хранения экземпляра WebDriverWait текущего потока
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();

    // Дефолтный таймаут ожидания элементов на странице (в секундах)
    private static final long TIMEOUT_SECONDS = 20;

    // Приватный конструктор исключает возможность создания экземпляров класса через 'new'
    private WaitManager() {
    }

    /**
     * Возвращает экземпляр WebDriverWait для текущего потока.
     * Если объект еще не создан, инициализирует его (Ленивая инициализация).
     */
    public static WebDriverWait getWait() {
        // Проверяем, созданы ли уже ожидания для текущего потока
        if (waitThreadLocal.get() == null) {

            // Создаем новый WebDriverWait, привязывая его к драйверу текущего потока (Driver.getDriver())
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(TIMEOUT_SECONDS));

            // Сохраняем объект явного ожидания в контекст текущего потока
            waitThreadLocal.set(wait);
        }
        // Возвращаем существующий экземпляр ожидания для этого потока
        return waitThreadLocal.get();
    }

    /**
     * Очищает ссылку на WebDriverWait из памяти текущего потока.
     * Автоматически вызывается внутри Driver.quitDriver() в конце каждого теста.
     */
    public static void unload() {
        // Удаляем объект из ThreadLocal, предотвращая утечку памяти (Memory Leak)
        waitThreadLocal.remove();
    }
}
