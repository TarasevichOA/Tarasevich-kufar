package by.kufar;

import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Locale;

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

    private void performSearch(String query) {
        homePage.clickCookies();
        homePage.fillInputSearch(query);
        homePage.clickButtonSearch();
    }

    @Test
    @DisplayName("Checking text in Search field when product is absolutely not found")
    public void testNothingFound() {
        Faker faker = new Faker(new Locale("ru"));
        // Генерируем реальное РУССКОЕ слово + цифры, чтобы спровоцировать Kufar показать похожие товары

        String searchQuery = faker.regexify("[к]{40}");

        performSearch(searchQuery);

        String actualMessage = homePage.getEmptyResultMessage();
        Assertions.assertTrue(
                actualMessage.contains("Мы это не нашли"),
                "Ожидаемый текст не найден. Фактически на сайте: " + actualMessage
        );
    }

    @Test
    @DisplayName("Checking text in Search field when similar products are found")
    public void testNothingFound2() {
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
