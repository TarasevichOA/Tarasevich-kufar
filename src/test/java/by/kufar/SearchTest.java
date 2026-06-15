package by.kufar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SearchTest.class);
    private SearchPage searchPage;
    HomePage homePage = new HomePage();

    @BeforeEach
    public void setupSearch() {
        String mavenBrowser = System.getProperty("browser", "chrome");
        startBrowserContext(mavenBrowser);
        homePage.clickCookies();
        searchPage = new SearchPage();
    }

    @DisplayName("Checking text of search input placeholder")
    @Test
    public void testCheckSearchInputPlaceholder() {
        Assertions.assertEquals("Поиск объявлений", searchPage.getSearchInputPlaceholder());
        logger.info("Text of search input placeholder is equal{}", searchPage.getSearchInputPlaceholder());
    }

    @DisplayName("Checking search of first product")
    @Test
    public void testCheckSearchFirstProduct() {
        String searchQuery = "Механизм для открывания межкомнатных дверей.";
        searchPage.setInputFullText(searchQuery);
        searchPage.clickSuggestionText();
        String textFirstProduct = searchPage.verifyFirstResult();

        logger.info("Search of first product {}", textFirstProduct);
        assertTrue(textFirstProduct.contains("Механизм для открывания межкомнатных дверей."),
                "Первый товар не содержит 'adidas'. Фактическое название: " + textFirstProduct);
    }

    @DisplayName("Checking text of search input placeholder after it was cleared")
    @Test
    public void testClearSearchInput() {
        String searchQuery = "Механизм для открывания межкомнатных дверей.";
        searchPage.setInputFullText(searchQuery);
        searchPage.clickSuggestionText();
        searchPage.clearSearchInput();
        logger.info("Checking text of search input placeholder after it was cleared {}", searchPage.getSearchInputPlaceholder());

        Assertions.assertEquals("Поиск объявлений", searchPage.getSearchInputPlaceholder());
    }
}
