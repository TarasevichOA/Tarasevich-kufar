package by.kufar;

import by.kufar.driver.Driver;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class HomeTest extends BaseTest {
    @ParameterizedTest(name = "Checking CopyRights in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testCopyRights(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();

        String textActualCopyRights = homePage.getCopyRights();
        Assertions.assertEquals("Куфар — площадка объявлений Беларуси. ООО «Куфар Тех» включено в " +
                "государственный информационный ресурс «Реестр рекламораспространителей»", textActualCopyRights);
    }

    @ParameterizedTest(name = "Checking Button Auth in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testButtonAuthText(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();
        String textActualButtonAuth = homePage.getButtonAuthText();
        Assertions.assertEquals("Войти", textActualButtonAuth);
    }

    @ParameterizedTest(name = "Checking text of Placeholder in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testPlaceholderText(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();
        String checkText = "Поиск объявлений";
        String actualText = homePage.getPlaceholderText();
        Assertions.assertTrue(
                actualText.contains(checkText),
                "Текст '" + checkText + "' не найден в actualText: " + actualText
        );
    }

    private void performSearch(String query) {
        homePage.clickCookies();
        homePage.fillInputSearch(query);
        homePage.clickButtonSearch();
    }

    @ParameterizedTest(name = "Checking text in Search field when product is absolutely not found in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testNothingFound(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();
        Faker faker = new Faker(new Locale("ru"));
        String randomChar = faker.options().option("абвгдеёжзийклмнопрстуфхцчшщъыьэюя".split(""));
        String searchQuery = faker.regexify("[" + randomChar + "]{40}");

        performSearch(searchQuery);

        String actualMessage = homePage.getEmptyResultMessage();
        Assertions.assertTrue(
                actualMessage.contains("Мы это не нашли"),
                "Ожидаемый текст не найден. Фактически на сайте: " + actualMessage
        );
    }

    @ParameterizedTest(name = "Checking text in Search field when similar products are found in browser: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testNothingFound2(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();
        Faker faker = new Faker(new Locale("ru"));
        String searchQuery = faker.commerce().department() + faker.number().digits(4);

        performSearch(searchQuery);

        String actualMessage = homePage.getEmptyResultMessage2();
        Assertions.assertTrue(
                actualMessage.contains("нет точных совпадений"),
                "Ожидаемый текст не найден. Фактически на сайте: " + actualMessage
        );
    }
}
