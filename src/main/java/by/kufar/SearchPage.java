package by.kufar;

import by.kufar.basepage.BasePage;
import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {
    private final String INPUT_SEARCH_BAR = "//input[@type='text' and contains(@placeholder, 'Поиск объявлений')]";
    private final String SUGGESTION_TEXT= "//span[@class='styles_suggestion__text__YOUxM']";
    private final String FIRST_PRODUCT = "//h3[@class='styles_title__F3uIe' and text() = 'Механизм для открывания межкомнатных дверей.']";


    public SearchPage() {
        super();
    }

    public String getSearchInputPlaceholder() {
        return Driver.getDriver().findElement(By.xpath(INPUT_SEARCH_BAR)).getAttribute("placeholder");
    }

    public void setInputFullText(String textSearch) {
        WaitManager.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(INPUT_SEARCH_BAR))).sendKeys(textSearch);
    }

    public void clickSuggestionText() {
        WebElement suggestionText = WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUGGESTION_TEXT)));
        suggestionText.click();
    }

    public String verifyFirstResult() {
        return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(FIRST_PRODUCT))).getText();
    }

    public void clearSearchInput() {
        Driver.getDriver().findElement(By.xpath(INPUT_SEARCH_BAR)).clear();
    }
}
