package by.kufar.driver;

import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitManager {
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static final long TIMEOUT_SECONDS = 20;

    private WaitManager() {}

    public static WebDriverWait getWait() {
        if (waitThreadLocal.get() == null) {
            // Driver.getDriver() вернет правильный браузер (Chrome, FF или Edge)
            // в зависимости от того, что было установлено для текущего потока
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(TIMEOUT_SECONDS));
            waitThreadLocal.set(wait);
        }
        return waitThreadLocal.get();
    }

    public static void unload() {
        waitThreadLocal.remove();
    }
}
