package by.kufar.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Ищет в src/test/resources/features
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "by.kufar.bdd") // Путь к вашим шагам
// ИСПРАВЛЕНО: убран пробел после 'pretty,'
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class RunCucumberTest {

}
