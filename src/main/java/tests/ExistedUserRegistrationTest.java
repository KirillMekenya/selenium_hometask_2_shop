package tests;

import elements.SignInPageElements;
import exceptions.ValueDoesNotMatch;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExistedUserRegistrationTest {
    public static void main(String[] args) throws ValueDoesNotMatch {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://automationpractice.com/index.php");

        SignInPageElements signInPageElements = new SignInPageElements();
        WebElement signInBtn = driver.findElement(signInPageElements.getSignInBtn());


        try {
            signInBtn.click();
            WebElement emaiRegisterInput = driver.findElement(signInPageElements.getEmailCreateUser());
            WebElement submitCreateUserBtn = driver.findElement(signInPageElements.getSubmitCreateUserBtn());
            emaiRegisterInput.click();
            emaiRegisterInput.sendKeys("mekenya93@gmail.com");
            submitCreateUserBtn.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(signInPageElements.getErrorMessage()));
            WebElement errorMessage = driver.findElement(signInPageElements.getErrorMessage());
            if (!errorMessage.getText().equals("An account using this email address has already been registered. " +
                    "Please enter a valid password or request a new one.")) {
                throw new ValueDoesNotMatch("Отсутсвует сообщение об ошибке о существующем пользователе");
            }
        } finally {
            driver.close();
        }
    }
}
