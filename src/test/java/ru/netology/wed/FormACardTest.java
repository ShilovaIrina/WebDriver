package ru.netology.wed;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormACardTest {
    private WebDriver driver;

    @BeforeAll
    public static void setAll() {
        WebDriverManager.chromedriver().setup();
        // метод, запускающий вебдрайвер менеджер перед каждый тестом
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void positiveTest() {
        //для поля имя фамилия
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        //для поля телефона
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999959");
        //для чекбокса + действие Click
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        //для кнопки "Отправить" + действие Click
        driver.findElement(By.cssSelector("button.button")).click();
        // для ответного сообщения после отправки заявки. Проверяем, что наш текст совпадает
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id='order-success']"));
        var actualText = actualTextElement.getText().trim(); // метод trim возвращает копию строки с пропущенными начальными и конечными пробелами
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

}
