package by.kufar.bdd.runners;


import org.junit.platform.suite.api.*;

/**
 * Класс-раннер для запуска Cucumber-тестов через JUnit 5 Platform.
 * При запуске этого класса стартует выполнение всех связанных .feature файлов.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
public class RunCucumberTest {

}
