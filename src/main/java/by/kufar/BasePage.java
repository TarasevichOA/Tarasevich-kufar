package by.kufar;

import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public BasePage() {
        this.driver = Driver.getDriver();
        this.wait = WaitManager.getWait(); // Получение синглтона
    }
    protected static final Logger logger = LogManager.getLogger();
}

