package by.kufar;

import by.kufar.driver.Driver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeEach
    public void beforeEach() {
        homePage = new HomePage();
        homePage.open();
    }

    @AfterEach
    public void afterEach() {
        Driver.quitDriver();
    }
}
