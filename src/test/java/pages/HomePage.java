package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    // Локаторы
    private By orderButtonTop = By.className("Button_Button__ra12g");
    private By orderButtonBottom = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private By faqQuestion = By.xpath("//div[@data-accordion-component='AccordionItemButton']");
    private By faqAnswer = By.xpath("//div[@class='accordion__panel']/p"); // Локатор для ответа
    private By cookieBanner = By.id("rcc-confirm-button"); // Локатор для кнопки закрытия куки-баннера

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonButtonBody() {
        driver.findElement(orderButtonBottom).click();
    }

    public void expandFAQQuestion(int index) {
        WebElement question = driver.findElements(faqQuestion).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        question.click();
    }

    public String getFAQAnswer(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[@class='accordion__panel']/p)[" + (index + 1) + "]")
        ));
        return answerElement.getText();
    }

    // Метод для закрытия куки-баннера
    public void closeCookieBanner() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        if (driver.findElements(cookieBanner).size() > 0) { // Проверяем, есть ли баннер
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(cookieBanner));
            cookieButton.click(); // Закрываем баннер
        }
    }
}