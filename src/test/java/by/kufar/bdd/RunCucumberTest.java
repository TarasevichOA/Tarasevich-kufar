package by.kufar.bdd;


import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class RunCucumberTest {

}
