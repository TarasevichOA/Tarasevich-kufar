package by.kufar;

import by.kufar.driver.Driver;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class HomeTest extends BaseTest {

    @DisplayName("Checking CopyRights")
    @ParameterizedTest(name = "Проверка CopyRights в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"}) // Перечисляем браузеры
    public void testCopyRights(String browser) {
        // Явно переопределяем браузер для текущего потока перед любыми действиями!
        Driver.setBrowserName(browser);
        homePage.open();

            String textActualCopyRights = homePage.getCopyRights();
            Assertions.assertEquals("Куфар — площадка объявлений Беларуси. ООО «Куфар Тех» включено в " +
                    "государственный информационный ресурс «Реестр рекламораспространителей»", textActualCopyRights);
        }

    @DisplayName("Checking Button Auth")
    @ParameterizedTest(name = "Проверка CopyRights в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    public void testButtonAuthText(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();
        String textActualButtonAuth = homePage.getButtonAuthText();
        Assertions.assertEquals("Войти", textActualButtonAuth);
    }

    @DisplayName("Checking text of Placeholder")
    @ParameterizedTest(name = "Проверка CopyRights в браузере: {0}")
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

    @ParameterizedTest(name = "Проверка CopyRights в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    @DisplayName("Checking text in Search field when product is absolutely not found")
    public void testNothingFound(String browser) {
        Driver.setBrowserName(browser);
        homePage.open();
        Faker faker = new Faker(new Locale("ru"));
        // Генерируем реальное РУССКОЕ слово + цифры, чтобы спровоцировать Kufar показать похожие товары
        // Выбираем одну случайную букву
        String randomChar = faker.options().option("абвгдеёжзийклмнопрстуфхцчшщъыьэюя".split(""));

        // Подставляем её в регулярное выражение
        String searchQuery = faker.regexify("[" + randomChar + "]{40}");

        performSearch(searchQuery);

        String actualMessage = homePage.getEmptyResultMessage();
        Assertions.assertTrue(
                actualMessage.contains("Мы это не нашли"),
                "Ожидаемый текст не найден. Фактически на сайте: " + actualMessage
        );
    }

    @ParameterizedTest(name = "Проверка CopyRights в браузере: {0}")
    @ValueSource(strings = {"chrome", "firefox", "edge"})
    @DisplayName("Checking text in Search field when similar products are found")
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
