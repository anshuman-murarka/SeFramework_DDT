package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BasePage {

    @FindBy(xpath = "//*[@id='user-name']")
    WebElement username;
    @FindBy(xpath = "//*[@id='password']")
    WebElement password;
    @FindBy(xpath = "//*[@id='login-button']")
    WebElement buttonLogin;
    @FindBy(xpath = "//*[@id='login_button_container']/div/form/h3")
    WebElement textError;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public LoginPage enterUsername(String value) {
        enterText(username, value);
        return this;
    }

    public LoginPage enterPassword(String value) {
        enterText(password, value);
        return this;
    }

    public LoginPage clickLogin() {
        click(buttonLogin);
        return this;
    }

    public String readErrorMessage() {
        return readText(textError);
    }

}
