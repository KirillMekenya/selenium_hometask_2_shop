import elements.SignInPageElements;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationTest {

    private SignInPageElements signInPageElements;
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String ALREADY_REGISTERED_USER_MESSAGE = "An account using this email address has already been registered. Please enter a valid password or request a new one.";
    private static final String ALREADY_REGISTERED_USER_EMAIL = "mekenya93@gmail.com";

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        signInPageElements = new SignInPageElements();
        driver.get("http://automationpractice.com/index.php");
    }

    @AfterAll
    public void finish() {
        driver.manage().deleteAllCookies();
        driver.close();
    }

    @Test
    @DisplayName("Регистрация с уже зарегестрированной почтой")
    public void existedUserRegistration() {
        WebElement signInBtn = driver.findElement(signInPageElements.getSignInBtn());
        signInBtn.click();
        WebElement emaiRegisterInput = driver.findElement(signInPageElements.getEmailCreateUser());
        WebElement submitCreateUserBtn = driver.findElement(signInPageElements.getSubmitCreateUserBtn());
        emaiRegisterInput.click();
        emaiRegisterInput.sendKeys(ALREADY_REGISTERED_USER_EMAIL);
        submitCreateUserBtn.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(signInPageElements.getErrorMessage()));
        WebElement errorMessage = driver.findElement(signInPageElements.getErrorMessage());
        assertEquals(ALREADY_REGISTERED_USER_MESSAGE, errorMessage.getText(),
                "Please enter a valid password or request a new one.");
    }
}
