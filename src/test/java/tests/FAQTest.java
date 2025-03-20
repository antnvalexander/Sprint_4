package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FAQTest {
    private WebDriver driver;
    private HomePage homePage;

    @Before
    public void setUp() {
        // Инициализация WebDriver
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        driver.get("https://qa-scooter.praktikum-services.ru/");

        // Закрытие куки-баннера
        homePage.closeCookieBanner();
    }

    @Test
    public void testFAQSection() {
        List<String> list = List.of("Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
                "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
                "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
                "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
                "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
                "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
                "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
                "Да, обязательно. Всем самокатов! И Москве, и Московской области.");
        //Счетчик, который увеличивается при каждом открывании очередного вопроса
        int index = 0;

        //Проходимся по списку ответов, берем счетчик, открываем по его номеру вопрос, и сравниваем его с ответом из списка list
        for (String str : list) {
            homePage.expandFAQQuestion(index);
            String answer = homePage.getFAQAnswer(index);
            index++;
            assertEquals(str, answer);
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}