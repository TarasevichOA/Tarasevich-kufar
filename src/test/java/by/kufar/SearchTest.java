package by.kufar;

import by.kufar.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SearchTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(SearchTest.class);
    private SearchPage searchPage;

    @BeforeEach
    public void setupSearchContext(TestInfo testInfo) {
        String browser = testInfo.getDisplayName()
                .replaceAll(".*:\\s*", "")
                .trim();

        if (browser.isEmpty() || browser.contains(" ") || browser.contains("()")) {
            browser = System.getProperty("browser", "chrome");
        }

        Driver.setBrowserName(browser);

        homePage.open();
        homePage.clickCookies();
        searchPage = new SearchPage();
    }

    @ParameterizedTest(name = "Checking text of search input placeholder in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckSearchInputPlaceholder(String browser) {
        String placeholder = searchPage.getSearchInputPlaceholder();
        Assertions.assertEquals("Поиск объявлений", placeholder);

        // Используем browser в логах, чтобы переменная не была серой
        logger.info("Браузер [{}]: Text of search input placeholder is equal: {}", browser, placeholder);
    }

    @ParameterizedTest(name = "Checking search of first product in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckSearchFirstProduct(String browser) {
        String searchQuery = "Механизм для открывания межкомнатных дверей.";
        searchPage.setInputFullText(searchQuery);
        searchPage.clickSuggestionText();
        String textFirstProduct = searchPage.verifyFirstResult();

        logger.info("Браузер [{}]: Search of first product: {}", browser, textFirstProduct);
        assertTrue(textFirstProduct.contains(searchQuery),
                "Первый товар не содержит '" + searchQuery + "'. Фактическое название: " + textFirstProduct);
    }

    @ParameterizedTest(name = "Checking text of search input placeholder after it was cleared in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testClearSearchInput(String browser) {
        String searchQuery = "Механизм для открывания межкомнатных дверей.";
        searchPage.setInputFullText(searchQuery);
        searchPage.clickSuggestionText();
        searchPage.clearSearchInput();

        String placeholder = searchPage.getSearchInputPlaceholder();
        logger.info("Браузер [{}]: Checking text of search input placeholder after it was cleared: {}", browser, placeholder);

        Assertions.assertEquals("Поиск объявлений", placeholder);
    }
}
