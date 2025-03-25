package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;
import pages.OrderPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private HomePage homePage;
    private OrderPage orderPage;

    private final String name;
    private final String surname;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String period;
    private final String comment;

    public OrderTest(String name, String surname, String address, String metroStation, String phone, String date, String period, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "Москва, ул. Ленина, 1", "Черкизовская", "89999999999", "01.01.2023", "сутки", "Позвонить за час"},
                {"Петр", "Петров", "Санкт-Петербург, Невский проспект, 10", "Площадь Революции", "88888888888", "02.02.2023", "двое суток", "Не звонить"}
        });
    }

    @Before
    public void setUp() {
        // Инициализация WebDriver
        driver = new ChromeDriver();
        homePage = new HomePage(driver);
        orderPage = new OrderPage(driver);
        driver.get("https://qa-scooter.praktikum-services.ru/");

        // Закрытие куки-баннера
        homePage.closeCookieBanner();
    }

    @Test
    public void testOrderFlowFromHeader() {
        homePage.clickOrderButtonTop();
        orderPage.fillOrderForm(name, surname, address, metroStation, phone);
        orderPage.fillRentalDetails(date, period, comment);
        orderPage.confirmOrder();
        String successMessage = orderPage.getOrderSuccessMessage();
        String orderNumber = orderPage.getOrderNumber();
        assertEquals(String.format("Заказ оформлен\n"
                + "Номер заказа: %s.  Запишите его:\n"
                + "пригодится, чтобы отслеживать статус", orderNumber), successMessage);
    }

    @Test
    public void testOrderFlowFromBody() {
        homePage.clickOrderButtonButtonBody();
        orderPage.fillOrderForm(name, surname, address, metroStation, phone);
        orderPage.fillRentalDetails(date, period, comment);
        orderPage.confirmOrder();
        String successMessage = orderPage.getOrderSuccessMessage();
        String orderNumber = orderPage.getOrderNumber();
        assertEquals(String.format("Заказ оформлен\n"
                + "Номер заказа: %s.  Запишите его:\n"
                + "пригодится, чтобы отслеживать статус", orderNumber), successMessage);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}