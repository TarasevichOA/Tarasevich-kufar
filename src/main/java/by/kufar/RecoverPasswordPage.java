package by.kufar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecoverPasswordPage extends BasePage {
    private final String TITLE = "//h1[contains(@class, 'styles_title__eUxiN')]";
    private final String INPUT_EMAIL = "//input[@id='email']";
    private final String BUTTON_AUTH = "//button[@type='submit']";
    private final String EXTENDED_DESCRIPTION_ERROR = "//span[contains(text(), 'Введите e-mail')]";
    private final String EXTENDED_ERROR_WRONG_EMAIL = "//span[contains(text(), 'Проверьте введенный e-mail - неправильный формат')]";
    private final String COOKIES = "//*[@id=\"__next\"]/div[3]/div/div/div/div/button[2]";

    private final String URL_RECOVER_PASSWORD = "https://www.kufar.by/account/recovery";

    public RecoverPasswordPage() {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL_RECOVER_PASSWORD);
    }

    public void clickCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cookies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COOKIES)));
        cookies.click();
    }

    public String getTitleText() {
        return driver.findElement(By.xpath(TITLE)).getText();
    }

    public void setInputEmail(String email) {
        driver.findElement(By.xpath(INPUT_EMAIL)).sendKeys(email);
    }

    public void clickButtonAuth() {
        driver.findElement(By.xpath(BUTTON_AUTH)).click();
    }

    public String getExtendedDescriptionError() {
        WebElement element = driver.findElement(By.xpath(INPUT_EMAIL));
        Actions actions = new Actions(driver);

        // Нажатие в 50 пикселях вправо и 50 вниз от верхнего левого угла элемента
        actions.moveToElement(element, 50, 50).click().perform();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EXTENDED_DESCRIPTION_ERROR))).getText();
    }

    public String getExtendedErrorWrongEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EXTENDED_ERROR_WRONG_EMAIL))).getText();
    }

    public WebElement getAuthButton(){
        return driver.findElement(By.xpath(BUTTON_AUTH));
    }
}
