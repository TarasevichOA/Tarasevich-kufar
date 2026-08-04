package by.kufar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Пример демонстрационного класса для работы с логированием Log4j2.
 * В реальном проекте этот код пишется внутри тестов или Page Object классов.
 */
public class LoggerDemo {
    private static final Logger logger = LogManager.getLogger(LoggerDemo.class);

    /**
     * Точка входа для демонстрации (в реальных UI-тестах этот метод не нужен,
     * там код исполняется внутри тестовых методов @Test).
     */
    public static void main(String[] args) {

        logger.info("Hello INFO from logger. Сообщаем о штатном действии в тесте.");

        logger.warn("It is a WARN. Предупреждение о потенциальной проблеме.");

        logger.error("Here is an ERROR. Запись текстовой ошибки в лог.");

        try {
            int a = 1 / 0;
        } catch (Exception exception) {
            logger.error("Real ERROR with error message: Произошло падение во время выполнения!", exception);
        }
    }
}
