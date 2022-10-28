import pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationTest {

    private MainPage mainPage;
    private WebDriver driver;

    private static final String ALREADY_REGISTERED_USER_MESSAGE = "An account using this email address has already been registered. Please enter a valid password or request a new one.";
    private static final String ALREADY_REGISTERED_USER_EMAIL = "mekenya93@gmail.com";

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver, wait);
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
        String errorMessage = mainPage.clickSignInButton()
                                      .inputRegistrationEmail(ALREADY_REGISTERED_USER_EMAIL)
                                      .clickSubmitRegistrationButton()
                                      .getErrorMessageText();
        assertEquals(ALREADY_REGISTERED_USER_MESSAGE, errorMessage,
                "Please enter a valid password or request a new one.");
    }
}
