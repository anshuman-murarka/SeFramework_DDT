package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForVisibility(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void enterText(WebElement element, String value) {
        try {
            waitForVisibility(element);
            element.clear();
            element.sendKeys(value);
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public void click(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (TimeoutException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public String readText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }
}
