package by.kufar.bdd.steps;

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
    public void verifyAdvanceSearchTitle(String expectedTitle) {
        String textTitleOnRecoverPasswordPage = recoverPasswordPage.getTitleText();
        Assertions.assertEquals(expectedTitle, textTitleOnRecoverPasswordPage);
    }
}
