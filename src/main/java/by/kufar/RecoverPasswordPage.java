package by.kufar;

import by.kufar.basepage.BasePage;
import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.openqa.selenium.By;
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


    public void open() {
        Driver.getDriver().get(URL_RECOVER_PASSWORD);
    }

    public void clickCookies() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        WebElement cookies = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(COOKIES)));
        cookies.click();
    }

    public String getTitleText() {
        return Driver.getDriver().findElement(By.xpath(TITLE)).getText();
    }

    public void setInputEmail(String email) {
        Driver.getDriver().findElement(By.xpath(INPUT_EMAIL)).sendKeys(email);
    }

    public void clickButtonAuth() {
        Driver.getDriver().findElement(By.xpath(BUTTON_AUTH)).click();
    }

    public String getExtendedDescriptionError() {
        // 1. Находим базовый веб-элемент (поле ввода email) по его XPath-локатору.
        // Внимание: здесь используется прямой поиск findElement(), который упадет, если элемент еще не успел отрисоваться.
        WebElement element = Driver.getDriver().findElement(By.xpath(INPUT_EMAIL));

        // 2. Создаем объект Actions — это специальный инструмент Selenium для сложных симуляций пользователя
        // (движения мыши, drag-and-drop, зажатие клавиш, контекстные клики).
        Actions actions = new Actions(Driver.getDriver());

        // 3. Выстраиваем и выполняем цепочку действий мыши:
        actions.moveToElement(element, 50, 50) // Переместить курсор к элементу 'element', но со смещением на 50 пикселей вправо (X) и 50 пикселей вниз (Y) от его центра
                .click()                        // Кликнуть левой кнопкой мыши в этой новой точке
                .perform();                     // ВАЖНО: запустить всю цепочку действий на выполнение (без perform() ничего не произойдет)

        // 4. Ожидаем появления сообщения об ошибке и возвращаем его текст:
        return WaitManager.getWait()
                // Ждем (до 20 секунд), пока элемент с расширенным описанием ошибки станет видимым на экране
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EXTENDED_DESCRIPTION_ERROR)))
                // Как только элемент появился, мгновенно считываем его видимый текст и возвращаем из метода
                .getText();

    }

    public String getExtendedErrorWrongEmail() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(EXTENDED_ERROR_WRONG_EMAIL))).getText();
    }

    public WebElement getAuthButton() {
        return Driver.getDriver().findElement(By.xpath(BUTTON_AUTH));
    }
}
