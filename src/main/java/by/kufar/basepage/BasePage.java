package by.kufar.basepage;

import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    //protected final Logger logger = LogManager.getLogger(this.getClass());
    public BasePage() {
        this.driver = Driver.getDriver();
        this.wait = WaitManager.getWait(); // Получение синглтона
    }
}
