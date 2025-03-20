package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    // Локаторы
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationField = By.xpath("//input[@placeholder='* Станция метро']");
    private By metroStationOption = By.xpath("//div[@class='select-search__option']");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[contains(text(), 'Далее')]");
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.xpath("//div[contains(text(), 'Срок аренды')]");
    private By colorCheckbox = By.xpath("//input[@id='black']");
    private By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private By orderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private By confirmButton = By.xpath("//button[contains(text(), 'Да')]");
    private By orderSuccessMessage = By.xpath("//div[contains(text(), 'Заказ оформлен')]");
    private By selectedDate = By.className("react-datepicker__day--selected");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Заполнение формы заказа
    public void fillOrderForm(String name, String surname, String address, String metroStation, String phone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Заполнение полей
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        // Выбор станции метро
        WebElement metroStationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(metroStationField));
        metroStationElement.click(); // Открываем выпадающий список
        metroStationElement.sendKeys(metroStation); // Вводим текст

        // Ожидание появления вариантов и выбор нужного
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(text(), '" + metroStation + "')]")
        )).click();

        // Заполнение телефона и переход к следующему шагу
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполнение формы аренды
    public void fillRentalDetails(String date, String period, String comment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ожидание и заполнение поля даты
        WebElement dateElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dateElement);
        dateElement.sendKeys(date);
        dateElement.click();
        WebElement dateInDatePicker = driver.findElement(selectedDate);
        dateInDatePicker.click();


        // Выбор срока аренды
        driver.findElement(rentalPeriodField).click();
        WebElement periodElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), '" + period + "')]")
        ));
        periodElement.click();

        // Выбор цвета
        driver.findElement(colorCheckbox).click();

        // Ввод комментария
        WebElement commentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(commentField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", commentElement);
        commentElement.sendKeys(comment);

        // Нажатие кнопки "Заказать"
        driver.findElement(orderButton).click();
    }

    // Подтверждение заказа
    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmButton)).click();
    }

    // Получение сообщения об успешном заказе
    public String getOrderSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage)).getText();
    }

    // Получение номера заказа
    public String getOrderNumber() {
        //С помощью регулярного выражения ищем номер заказа в тексте
        return driver.findElement(orderSuccessMessage).getText().replaceAll("[^0-9]", "");
    }
}