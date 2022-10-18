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

public class SignInTest {
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
            WebElement emailInputField = driver.findElement(signInPageElements.getEmailInputField());
            WebElement pswdInputField = driver.findElement(signInPageElements.getPasswordInputField());
            WebElement submitSignInBtn = driver.findElement(signInPageElements.getSubmitButton());
            emailInputField.click();
            emailInputField.sendKeys("mekenya93@gmail.com");
            pswdInputField.click();
            pswdInputField.sendKeys("561337");
            submitSignInBtn.click();
            WebElement accHeader = driver.findElement(signInPageElements.getAccHeader());
            wait.until(ExpectedConditions.visibilityOf(accHeader));
            if (!accHeader.getText().equals("Kiryl Miakenia")) {
                throw new ValueDoesNotMatch("Вход не выполнен или выполнен под другим пользователем");
            }
        } finally {
            driver.close();
        }
    }
}
