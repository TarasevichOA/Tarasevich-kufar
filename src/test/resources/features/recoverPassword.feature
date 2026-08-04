Feature: Test scenarios for RecoverPasswordPage

  Scenario: Test for verify title recover password form

    Given User opens Recover Password Page for Kufar
    When User click Cookies on opens recover password page
    Then User see title - "Восстановление пароля"
