package by.kufar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends BasePage {
    private final String INPUT_SEARCH_BAR = "//input[@type='text' and contains(@placeholder, 'Поиск объявлений')]";
    private final String FIRST_PRODUCT = "//h3[@class='styles_title__F3uIe' and text() = 'Механизм для открывания межкомнатных дверей.']";


    public SearchPage() {
        super();
    }

    public String getSearchInputPlaceholder() {
        return driver.findElement(By.xpath(INPUT_SEARCH_BAR)).getAttribute("placeholder");
    }

    public void setInputFullText(String textSearch) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(INPUT_SEARCH_BAR))).sendKeys(textSearch);
    }

    public String verifyFirstResult() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIRST_PRODUCT))).getText();
    }

    public void clearSearchInput() {
        driver.findElement(By.xpath(INPUT_SEARCH_BAR)).clear();
    }
}
