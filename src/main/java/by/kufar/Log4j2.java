package by.kufar;

import by.kufar.basepage.BasePage;

public class Log4j2 extends BasePage {
    public static void main(String[] args) {
        logger.info("Hello INFO from logger.");
        logger.warn("It is a WARN.");
        logger.error("Here is an ERROR.");
        try {
            int a = 1 / 0;
        } catch (Exception exception) {
            logger.error("Real ERROR with error message:", exception);
        }
    }
}
