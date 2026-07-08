package by.kufar.basepage;

import by.kufar.driver.Driver;
import by.kufar.driver.WaitManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

    // abstract запрещает создавать объект самого BasePage (нельзя сделать new BasePage)
    // Этот класс служит только фундаментом для реальных страниц (HomePage, SearchPage и др.)
    public abstract class BasePage {

        // protected позволяет классам-наследникам напрямую использовать драйвер и ожидания
        // protected — переменная доступна внутри этого класса и во всех классах-наследниках (например, в HomePage, SearchPage)
        // WebDriver — интерфейс Selenium для управления браузером (клики, ввод текста, навигация)
        protected WebDriver driver;

        // protected — переменная также открыта для всех дочерних классов страниц
        // WebDriverWait — класс Selenium для реализации явных ожиданий (ждёт, пока элемент станет видимым или кликабельным)
        protected WebDriverWait wait;


        // final гарантирует, что логгер нельзя перезаписать в коде после инициализации.
        // getClass() динамически берет имя конечного класса (например, если вызван HomePage,
        // то в лог запишется именно имя HomePage, а не BasePage).
        protected static final Logger logger = LogManager.getLogger();

        // Конструктор автоматически срабатывает каждый раз, когда создается любая страница
        public BasePage() {
            // Запрашиваем из синглтона потокобезопасный браузер для текущего потока
            this.driver = Driver.getDriver();

            // Запрашиваем из синглтона настроенный экземпляр явных ожиданий WebDriverWait
            this.wait = WaitManager.getWait();
        }
    }
