package by.kufar;

import by.kufar.basepage.BasePage;
import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.Set;

public class AuthFormPage extends BasePage{
    private final String TITEL = "//span[@class='TabItem_styles_title__Ij2Va' and text() = 'Вход']";
    private final String INPUT_NAME = "//input[@placeholder='Email или номер телефона\t']";
    private final String INPUT_PASSWORD = "//input[@placeholder='Пароль']";
    private final String BUTTON_SUBMIT = "//button[contains(@class, 'Button_styles_button--green-accent__vLxrh Button_styles_button--full-width__MZkn5')]";
    private final String ERROR_MESSAGE_NAME = "//span[contains(text(), 'Заполните обязательное поле')]";
    private final String ERROR_MESSAGE_PASSWORD = "//span[contains(text(), 'Введите пароль')]";
    private final String ERROR_MESSAGE_AUTH = "//p[contains(text(), 'Такого профиля не существует')]";
    private final String ERROR_MESSAGE_BLOCK_PROFILE = "//div[contains(text(), 'Профиль заблокирован и не может быть использован. Возможные причины блокировки ')]";

    private final String LINK_REGISTOR = "//span[text()= 'Регистрация']";
    private final String LINK_FORGOT_PASSWORD = "//a[@href='https://www.kufar.by/account/recovery']";
    private final String BUTTON_CLOSE = "(//span[@data-testid='popup-close'])[2]";

    public AuthFormPage() {
        super();
    }

    public String getTitleText() {
        return Driver.getDriver().findElement(By.xpath(TITEL)).getText();
    }

    public void setInputName(String name) {
        Driver.getDriver().findElement(By.xpath(INPUT_NAME)).sendKeys(name);
    }

    public void setInputPassword(String password) {
        Driver.getDriver().findElement(By.xpath(INPUT_PASSWORD)).sendKeys(password);
    }

    public void clickButtonSubmit() {
        // 1. Исправлен тип переменной на Wait<WebDriver>
        FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                // 2. Ускорили опрос (0.5 сек вместо 5 сек)
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .withMessage("Кнопка Submit не найдена за отведенное время");

        // 3. Добавлен @Override для чистоты кода
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BUTTON_SUBMIT)));
        element.click();
    }

    public String getErrorMessageName() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_NAME))).getText();
    }

    public String getErrorMessagePassword() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_PASSWORD))).getText();
    }

    public String getErrorMessageAuth() {
        FluentWait<WebDriver> wait = new FluentWait<>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500)) // 2. Ускорили опрос (0.5 сек вместо 5 сек)
                .ignoring(NoSuchElementException.class)
                .withMessage("Кнопка Submit не найдена за отведенное время");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_AUTH))).getText();
    }

    public String getErrorMessageBlockProfile() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ERROR_MESSAGE_BLOCK_PROFILE))).getText();
    }

    public WebElement getLinkForgotPassword() {
        return Driver.getDriver().findElement(By.xpath(LINK_FORGOT_PASSWORD));
    }

    public void clickLinkForgotPassword() {
        String mainWindow = Driver.getDriver().getWindowHandle();

        // 2. Кликаем по ссылке, открывающей новое окно
        Driver.getDriver().findElement(By.xpath(LINK_FORGOT_PASSWORD)).click();
        // 3. Получаем список всех дескрипторов
        Set<String> allWindows = Driver.getDriver().getWindowHandles();
        // 4. Переключаемся на новое окно
        for (String handle : allWindows) {
            if (!handle.equals(mainWindow)) {
                Driver.getDriver().switchTo().window(handle);
                break;
            }
        }
        // Теперь мы работаем в новом окне
        System.out.println("Новое окно: " + Driver.getDriver().getTitle());

        // Вернуться в основное окно (если нужно)
        // driver.switchTo().window(mainWindow);
    }

    public void clickLinkRegistration() {
        Driver.getDriver().findElement(By.xpath(LINK_REGISTOR)).click();
    }

    public void clickButtonClose() {
        Driver.getDriver().findElement(By.xpath(BUTTON_CLOSE)).click();
    }
}
