package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage extends BasePage {
    private final By emailInputField = By.id("email");
    private final By passwordInputField = By.id("passwd");
    private final By submitButton = By.xpath("//button[@id='SubmitLogin']/span");
    private final By emailCreateUser = By.id("email_create");
    private final By submitCreateUserBtn = By.id("SubmitCreate");
    private final By errorMessage = By.xpath("//div[@id='create_account_error']/ol/li");
    private final By errorAuthMessage = By.xpath("//div[@class='alert alert-danger']/ol/li");
    private final By invalidEmailElement = By.xpath("//div[@class='form-group form-error']/input");

    public SignInPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public SignInPage inputRegistrationEmail(String email) {
        driver.findElement(emailCreateUser).click();
        driver.findElement(emailCreateUser).sendKeys(email);
        return this;
    }

    public SignInPage clickSubmitRegistrationButton() {
        driver.findElement(submitCreateUserBtn).click();
        return this;
    }

    public String getErrorMessageText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText();
    }

    public SignInPage clickSubmitSignInButtonExpectedErrors() {
        driver.findElement(submitButton).click();
        return this;
    }

    public MyAccountPage clickSubmitSignInButtonExpectedSuccess() {
        driver.findElement(submitButton).click();
        return new MyAccountPage(driver, wait);
    }

    public SignInPage inputSignInEmail(String email) {
        driver.findElement(emailInputField).click();
        driver.findElement(emailInputField).sendKeys(email);
        return this;
    }

    public SignInPage inputSignInPassword(String password) {
        driver.findElement(passwordInputField).click();
        driver.findElement(passwordInputField).sendKeys(password);
        return this;
    }

    public String getErrorAuthMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorAuthMessage));
        return driver.findElement(errorAuthMessage).getText();
    }

    public boolean invalidEmailInputIsDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(invalidEmailElement));
        return driver.findElement(invalidEmailElement).isDisplayed();
    }
}
