package by.kufar.bdd;

import by.kufar.RecoverPasswordPage;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

public class RecoverPasswordPageSteps {
    private RecoverPasswordPage recoverPasswordPage;

    @Given("User opens Recover Password Page for Kufar")
    public void openRecoverPasswordPage() {
        recoverPasswordPage = new RecoverPasswordPage();
        recoverPasswordPage.open();
    }

    @When("User click Cookies on opens recover password page")
    public void clickCookies() {
        recoverPasswordPage.clickCookies();
    }

    @Then("User see title - {string}")
    // Передаем аргумент в метод, чтобы Cucumber мог подставить значение из сценария
    public void verifyAdvanceSearchTitle(String expectedTitle) {
        String textTitleOnRecoverPasswordPage = recoverPasswordPage.getTitleText();
        // Используем переданный аргумент вместо захардкоженной строки
        Assertions.assertEquals(expectedTitle, textTitleOnRecoverPasswordPage);
    }
}
