package by.kufar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
    private final String URL = "https://www.kufar.by/";
    private final String INPUT_SEARCH = "//input[@placeholder = 'Поиск объявлений']";
    private final String BUTTON_AUTH = "//button[text()='Войти']";
    private final String BUTTON_SEARCH = "//button[@class='styles_search_button__Ro1wM']";
    private final String COPY_RIGHTS = "//*[@id=\"footer-container\"]/div[3]/div[1]/p[1]";
    private final String COOKIES = "//*[@id=\"__next\"]/div[2]/div/div/div/div/button[2]";
    private final String EMPTY_RESULT = "//*[contains(text(), 'Мы это не нашли')]";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(URL);
    }

    public void clickCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cookies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COOKIES)));
        cookies.click();
    }

    public String getButtonAuthText() {
        return driver.findElement(By.xpath(BUTTON_AUTH)).getText();
    }

    public String getCopyRights() {
        return driver.findElement(By.xpath(COPY_RIGHTS)).getText();
    }

    public void clickButtonSearch() {
        driver.findElement(By.xpath(BUTTON_SEARCH)).click();
    }

    public void clickButtonAuth() {
        WebElement loginButton = driver.findElement(By.xpath(BUTTON_AUTH));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
        //driver.findElement(By.xpath(BUTTON_AUTH)).click();
    }

    public void fillInputSearch(String data) {
        driver.findElement(By.xpath(INPUT_SEARCH)).sendKeys(data);
    }

    public String getEmptyResultMessage() {
        WebElement getEmptyResultMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(EMPTY_RESULT)));
        return getEmptyResultMessage.getText();
    }

    public String getPlaceholderText() {
        WebElement element = driver.findElement(By.xpath(INPUT_SEARCH));
        return element.getAttribute("placeholder");
    }
}
