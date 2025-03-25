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
    private By orderButtonBottom = By.xpath("//div[@class='Home_RoadMap__2tal_']//button");
    private By cookieBanner = By.id("rcc-confirm-button"); // Локатор для кнопки закрытия куки-баннера
    private String faqQuestion = "//*[text()='%s']//..";
    private String faqAnswer = "//*[text()='%s']//../following-sibling::div/p"; // Локатор для ответа


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonButtonBody() {
        driver.findElement(orderButtonBottom).click();
    }

    public void expandFAQQuestion(String question) {
        WebElement questionElement = driver.findElement(By.xpath(String.format(faqQuestion, question)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", questionElement);
        questionElement.click();
    }

    public String getFAQAnswer(String question) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement answerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(faqAnswer, question))
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
