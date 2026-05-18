package by.kufar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeTest extends BaseTest {

    @Test
    public void testCopyRights() {
        String textActualCopyRights = homePage.getCopyRights();
        Assertions.assertEquals("Куфар — площадка объявлений Беларуси. ООО «Куфар Тех» включено в " +
                "государственный информационный ресурс «Реестр рекламораспространителей»", textActualCopyRights);
    }

    @Test
    public void testButtonAuthText() {
        String textActualButtonAuth = homePage.getButtonAuthText();
        Assertions.assertEquals("Войти", textActualButtonAuth);
    }

    @Test
    public void testPlaceholderText() {
        String checkText = "Поиск объявлений";
        String actualText = homePage.getPlaceholderText();
        Assertions.assertTrue(
                actualText.contains(checkText),
                "Текст '" + checkText + "' не найден в actualText: " + actualText
        );
    }

    @Test
    public void testNothingFound() {
        String searchQuery = "testtesttest";
        homePage.clickCookies();
        homePage.fillInputSearch(searchQuery);
        homePage.clickButtonSearch();
        Assertions.assertEquals("Мы это не нашли", homePage.getEmptyResultMessage());
    }
}
