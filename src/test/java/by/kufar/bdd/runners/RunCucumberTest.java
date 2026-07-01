package by.kufar.bdd.runners;


import org.junit.platform.suite.api.*;

/**
 * Класс-раннер для запуска Cucumber-тестов через JUnit 5 Platform.
 * При запуске этого класса стартует выполнение всех связанных .feature файлов.
 */
@Suite // 1. Указывает JUnit 5, что данный класс является тестовой сюитой (набором тестов)
@IncludeEngines("cucumber") // 2. Говорит JUnit-платформе использовать движок Cucumber для чтения и выполнения тестов
@SelectClasspathResource("features") // 3. Указывает путь в папке ресурсов (src/test/resources/features), где лежат .feature файлы с бизнес-сценариями
@ConfigurationParameter(
        key = "cucumber.plugin",
        // 4. Настройка плагинов Cucumber:
        // "pretty" — красиво форматирует вывод шагов тестов прямо в консоль в реальном времени
        // "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" — подключает адаптер Allure для автоматического сбора результатов и генерации красивых HTML-отчетов
        value = "pretty,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
public class RunCucumberTest {
    // Класс намеренно оставляется пустым.
    // Вся его работа заключается в метаданных (аннотациях) над ним.
}
