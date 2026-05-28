package by.kufar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class HomeTest extends BaseTest {
    HomePage homePage = new HomePage();

    @DisplayName("Checking CopyRights")
    @Test
    public void testCopyRights() {
        String textActualCopyRights = homePage.getCopyRights();
        Assertions.assertEquals("Куфар — площадка объявлений Беларуси. ООО «Куфар Тех» включено в " +
                "государственный информационный ресурс «Реестр рекламораспространителей»", textActualCopyRights);
    }

    @DisplayName("Checking Button Auth")
    @Test
    public void testButtonAuthText() {
        String textActualButtonAuth = homePage.getButtonAuthText();
        Assertions.assertEquals("Войти", textActualButtonAuth);
    }

    @DisplayName("Checking text of Placeholder")
    @Test
    public void testPlaceholderText() {
        String checkText = "Поиск объявлений";
        String actualText = homePage.getPlaceholderText();
        Assertions.assertTrue(
                actualText.contains(checkText),
                "Текст '" + checkText + "' не найден в actualText: " + actualText
        );
    }

    @DisplayName("Checking text in Search field when User can't find any product")
    @Test
    public void testNothingFound() {
        String searchQuery = "eeeereeterteyery";
        homePage.clickCookies();
        homePage.fillInputSearch(searchQuery);
        homePage.clickButtonSearch();
        Assertions.assertEquals("Мы это не нашли", homePage.getEmptyResultMessage());
    }

    @DisplayName("Checking text in Search field when User can't find any product")
    @Test
    public void testNothingFound2() {
        String searchQuery = "cattttttttttt";
        homePage.clickCookies();
        homePage.fillInputSearch(searchQuery);
        homePage.clickButtonSearch();
        Assertions.assertEquals("По вашему запросу нет точных совпадений, но мы подобрали похожие варианты", homePage.getEmptyResultMessage2());
    }
}
