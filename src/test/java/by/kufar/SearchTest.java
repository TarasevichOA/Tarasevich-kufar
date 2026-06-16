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
        // Динамически забираем имя браузера из названия текущего параметризованного теста
        String browser = testInfo.getDisplayName()
                .replaceAll(".*:\\s*", "")
                .trim();

        // Безопасный фолбэк на дефолт из Maven, если имя не распарсилось
        if (browser.isEmpty() || browser.contains(" ") || browser.contains("()")) {
            browser = System.getProperty("browser", "chrome");
        }

        // 1. Привязываем браузер к текущему потоку
        Driver.setBrowserName(browser);

        // 2. Открываем главную страницу (объект homePage уже инициализирован в BaseTest)
        homePage.open();

        // 3. Закрываем куки и готовим страницу поиска
        homePage.clickCookies();
        searchPage = new SearchPage();
    }

    @DisplayName("Checking text of search input placeholder")
    @ParameterizedTest(name = "Проверка плейсхолдера поиска в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCheckSearchInputPlaceholder(String browser) {
        String placeholder = searchPage.getSearchInputPlaceholder();
        Assertions.assertEquals("Поиск объявлений", placeholder);

        // Используем browser в логах, чтобы переменная не была серой
        logger.info("Браузер [{}]: Text of search input placeholder is equal: {}", browser, placeholder);
    }

    @DisplayName("Checking search of first product")
    @ParameterizedTest(name = "Проверка поиска первого товара в браузере: {0}")
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

    @DisplayName("Checking text of search input placeholder after it was cleared")
    @ParameterizedTest(name = "Проверка очистки строки поиска в браузере: {0}")
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