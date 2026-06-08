package by.kufar;

import by.kufar.basepage.BasePage;
import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    private final String URL = "https://www.kufar.by/";
    private final String INPUT_SEARCH = "//input[@placeholder = 'Поиск объявлений']";
    private final String BUTTON_AUTH = "//button[contains(@class, 'styles_button') and contains(@class, 'styles_outline')]";
    private final String BUTTON_SEARCH = "//button[@class='styles_search_button__Ro1wM']";
    private final String COPY_RIGHTS = "//*[@id=\"footer-container\"]/div[3]/div[1]/p[1]";
    private final String COOKIES = "//*[@id=\"__next\"]/div[2]/div/div/div/div/button[2]";
    private final String EMPTY_RESULT = "//*[contains(text(), 'Мы это не нашли')]";
    private final String EMPTY_RESULT2 = "//*[contains(text(), 'По вашему запросу нет точных совпадений, но мы подобрали похожие варианты')]";

    public HomePage() {
        super();
    }

    public void open() {
        // Безопасно открываем URL для текущего потока
        Driver.getDriver().get(URL);
        logger.info("Home page is opened");
    }

    public void clickCookies() {
        // Вызываем ожидание из ThreadLocal контейнера
        WebElement cookies = WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COOKIES)));
        cookies.click();
        logger.info("Cookie button is clicked");
    }

    /*  public String getButtonAuthText() {
        String buttonAuthText = Driver.getDriver().findElement(By.xpath(BUTTON_AUTH)).getText();
        logger.info("Button Auth {}", buttonAuthText);
        return buttonAuthText;
    }*/
    public String getButtonAuthText() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

        // Выводим URL для диагностики
        logger.info("DEBUG: Current URL is '{}'", Driver.getDriver().getCurrentUrl());

        // Ждем видимости элемента
        String buttonAuthText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(BUTTON_AUTH))
        ).getText();

        logger.info("Button Auth text is: '{}'", buttonAuthText);
        return buttonAuthText;
    }

    public String getCopyRights() {
        String copyrightsText = Driver.getDriver().findElement(By.xpath(COPY_RIGHTS)).getText();
        logger.info("Copyrights {}", copyrightsText);
        return copyrightsText;
    }

    public void clickButtonSearch() {
        Driver.getDriver().findElement(By.xpath(BUTTON_SEARCH)).click();
        logger.info("Button Search clicked successfully");
    }

    public void clickButtonAuth() {
        WebElement loginButton = Driver.getDriver().findElement(By.xpath(BUTTON_AUTH));
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", loginButton);
    }

    public void fillInputSearch(String data) {
        Driver.getDriver().findElement(By.xpath(INPUT_SEARCH)).sendKeys(data);
        logger.info("Filled search input with data: {}", data); // Полезно для логирования
    }

    public String getEmptyResultMessage() {
        WebElement getEmptyResultMessage = WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(EMPTY_RESULT)));
        return getEmptyResultMessage.getText();
    }
    public String getEmptyResultMessage2() {
        WebElement getEmptyResultMessage = WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(EMPTY_RESULT2)));
        return getEmptyResultMessage.getText();
    }

    public String getPlaceholderText() {
        WebElement element = Driver.getDriver().findElement(By.xpath(INPUT_SEARCH));
        String attr = element.getAttribute("placeholder");
        logger.info("Placeholder text {}", attr);
        return attr;
    }
}
