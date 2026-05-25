package by.kufar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest extends BaseTest {
    private SearchPage searchPage;

    @BeforeEach
    public void setupSearch() {
        homePage.clickCookies();
        searchPage = new SearchPage();
    }

    @Test
    public void testCheckSearchInputPlaceholder() {
        Assertions.assertEquals("Поиск объявлений", searchPage.getSearchInputPlaceholder());
    }

    @Test
    public void testCheckSearchFirstProduct() {
        String searchQuery = "Механизм для открывания межкомнатных дверей.";
        searchPage.setInputFullText(searchQuery);
        homePage.clickButtonSearch();
        String textFirstProduct = searchPage.verifyFirstResult();

        assertTrue(textFirstProduct.contains("Механизм для открывания межкомнатных дверей."),
                "Первый товар не содержит 'adidas'. Фактическое название: " + textFirstProduct);
    }
}
