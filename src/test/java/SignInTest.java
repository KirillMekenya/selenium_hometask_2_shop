import elements.SignInPageElements;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignInTest {
    private WebDriver driver;
    private WebDriverWait wait;
    SignInPageElements signInPageElements;
    WebElement signInBtn;
    private static final String USER_EMAIL = "mekenya93@gmail.com";
    private static final String INCORRECT_EMAIL = "me@mail.com";
    private static final String INVALID_EMAIL = "measdd";
    private static final String USER_PASSWORD = "561337";
    private static final String INCORRECT_USER_PASSWORD = "000";
    private static final String USER_NAME = "Kiryl Miakenia";
    private static final String INCORRECT_PASSWORD_MESSAGE = "Invalid password.";
    private static final String INCORRECT_LOGIN_MESSAGE = "Authentication failed.";

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        signInPageElements = new SignInPageElements();
        driver.get("http://automationpractice.com/index.php");
    }

    @BeforeEach
    public void refreshPage() {
        driver.manage().deleteAllCookies();
        signInBtn = driver.findElement(signInPageElements.getSignInBtn());
        signInBtn.click();
    }

    @AfterAll
    public void finish() {
        driver.manage().deleteAllCookies();
        driver.close();
    }

    @Test
    @Order(2)
    @DisplayName("Вход с неверным паролем")
    public void incorrectPasswordsignInTest() {
        WebElement emailInputField = driver.findElement(signInPageElements.getEmailInputField());
        WebElement pswdInputField = driver.findElement(signInPageElements.getPasswordInputField());
        WebElement submitSignInBtn = driver.findElement(signInPageElements.getSubmitButton());
        emailInputField.click();
        emailInputField.sendKeys(USER_EMAIL);
        pswdInputField.click();
        pswdInputField.sendKeys(INCORRECT_USER_PASSWORD);
        submitSignInBtn.click();
        assertEquals(INCORRECT_PASSWORD_MESSAGE, driver.findElement(signInPageElements.getErrorAuthMessage()).getText(),
                "Отсутсвует сообщение о неправильном пароле");
    }

    @Test
    @Order(3)
    @DisplayName("Вход с неверным логином")
    public void incorrectLoginSignInTest() {
        WebElement emailInputField = driver.findElement(signInPageElements.getEmailInputField());
        WebElement pswdInputField = driver.findElement(signInPageElements.getPasswordInputField());
        WebElement submitSignInBtn = driver.findElement(signInPageElements.getSubmitButton());
        emailInputField.click();
        emailInputField.sendKeys(INCORRECT_EMAIL);
        pswdInputField.click();
        pswdInputField.sendKeys(USER_PASSWORD);
        submitSignInBtn.click();
        assertEquals(INCORRECT_LOGIN_MESSAGE, driver.findElement(signInPageElements.getErrorAuthMessage()).getText(),
                "Отсутсвует сообщение о неправильном логине");
    }

    @Test
    @Order(1)
    @DisplayName("Проверка валидации email")
    public void invalidEmailSignInTest() {
        WebElement emailInputField = driver.findElement(signInPageElements.getEmailInputField());
        WebElement pswdInputField = driver.findElement(signInPageElements.getPasswordInputField());
        emailInputField.click();
        emailInputField.sendKeys(INVALID_EMAIL);
        pswdInputField.click();
        WebElement invalidEmailElement = driver.findElement(signInPageElements.getInvalidEmailElement());
        assertTrue(invalidEmailElement.isDisplayed(), "Отсутствует валидация email");
    }

    @Test
    @DisplayName("Успешный вход под существующим пользователем")
    public void signInTest() {
        WebElement emailInputField = driver.findElement(signInPageElements.getEmailInputField());
        WebElement pswdInputField = driver.findElement(signInPageElements.getPasswordInputField());
        WebElement submitSignInBtn = driver.findElement(signInPageElements.getSubmitButton());
        emailInputField.click();
        emailInputField.sendKeys(USER_EMAIL);
        pswdInputField.click();
        pswdInputField.sendKeys(USER_PASSWORD);
        submitSignInBtn.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(signInPageElements.getAccHeader())));
        WebElement accHeader = driver.findElement(signInPageElements.getAccHeader());
        assertEquals(USER_NAME, accHeader.getText(), "Вход не выполнен или выполнен под другим пользователем");
    }
}
