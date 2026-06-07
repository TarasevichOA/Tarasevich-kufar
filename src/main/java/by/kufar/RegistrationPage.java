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

public class RegistrationPage  extends BasePage {
    private final String INPUT_EMAIL = "//input[@type='email']";
    private final String INPUT_PASSWORD = "//input[@placeholder='Пароль']";
    private final String INPUT_REPEAT_PASSWORD = "//input[@placeholder='Подтвердите пароль']";
    private final String BUTTON_SUBMIT = "//button[span[contains(text(), 'Зарегистрироваться')]]";
    private final String ERROR_MESSAGE_EMAIL = "//span[contains(text(), 'Заполните обязательное поле')]";
    private final String ERROR_MESSAGE_WRONG_EMAIL = "//span[contains(text(), 'Проверьте введенный email - неправильный формат')]";
    private final String ERROR_MESSAGE_PASSWORD = "//ul[./text()[contains(., 'Пароль должен содержать:')]]";
    private final String ERROR_MESSAGE_REPEAT_PASSWORD = "//span[contains(text(), 'Пароли не совпадают. Введите пароль заново')]";
    private final String ERROR_MESSAGE_REPEAT_REGISTRATION = "//div[contains(text(), 'Произошла ошибка при активации профиля. Запросите новую ссылку, чтобы завершить регистрацию')]";
    private final String CHECKBOX_USER_AGREEMENT = "//label[contains(., 'Я принимаю условия')]//div[@role='checkbox']";
    private final String CAPTCHA = "//span[@id=\"recaptcha-anchor\"]";

    public RegistrationPage() {
        super();
    }

    public void setInputEmail(String email) {
        Driver.getDriver().findElement(By.xpath(INPUT_EMAIL)).sendKeys(email);
    }

    public void setInputPassword(String password) {
        Driver.getDriver().findElement(By.xpath(INPUT_PASSWORD)).sendKeys(password);
    }

    public void setInputRepeatPassword(String password) {
        Driver.getDriver().findElement(By.xpath(INPUT_REPEAT_PASSWORD)).sendKeys(password);
    }

    public void clickButtonSubmit() {
        WebElement button = Driver.getDriver().findElement(By.xpath((BUTTON_SUBMIT)));
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        // Убираем атрибут disabled и кликаем
        js.executeScript("arguments[0].removeAttribute('disabled'); arguments[0].click();", button);
    }

    public String getErrorMessageEmail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_EMAIL))).getText();
    }
    public String getErrorMessageWrongEmail() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_WRONG_EMAIL))).getText();
    }

    public String getErrorMessagePassword() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_PASSWORD))).getText();
    }

    public String getErrorMessageRepeatPassword() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_REPEAT_PASSWORD))).getText();
    }

    public String getErrorMessageWrongRegistration() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_REPEAT_REGISTRATION))).getText();
    }

    public void clickCheckboxUserAgreement() {
       /* WebElement checkbox = driver.findElement(By.xpath(CHECKBOX_USER_AGREEMENT));
        // Прокрутим к элементу, чтобы он точно был в области видимости
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
        // Кликаем напрямую через JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);*/

        Driver.getDriver().findElement(By.xpath(CHECKBOX_USER_AGREEMENT)).click();
    }

    public boolean isCheckboxSelected() {
        WebElement checkbox = Driver.getDriver().findElement(By.xpath(CHECKBOX_USER_AGREEMENT));
        String ariaChecked = checkbox.getAttribute("aria-checked");
        return "true".equals(ariaChecked);
    }

    public void clickCaptcha(){
        // 1. Ждем появления самого iframe (у него обычно заголовок "reCAPTCHA")
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[contains(@title, 'reCAPTCHA')]")));

        // 2. Теперь ищем чекбокс внутри фрейма
        WebElement recaptchaAnchor = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(CAPTCHA)));
        recaptchaAnchor.click();

        // 3. После клика возвращаемся к основному контенту страницы
        Driver.getDriver().switchTo().defaultContent();
    }
}
