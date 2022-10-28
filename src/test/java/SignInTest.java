import pages.MainPage;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MyAccountPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SignInTest {
    private WebDriver driver;

    private MainPage mainPage;

    private MyAccountPage myAccountPage;
    private static final String USER_EMAIL = "mekenya93@gmail.com";
    private static final String INCORRECT_EMAIL = "me@mail.com";
    private static final String INVALID_EMAIL = "measdd";
    private static final String USER_PASSWORD = "561337";
    private static final String INCORRECT_USER_PASSWORD = "000";
    private static final String USER_NAME = "Kiryl Miakenia";
    private static final String INCORRECT_PASSWORD_MESSAGE = "Invalid password.";
    private static final String INCORRECT_LOGIN_MESSAGE = "Authentication failed.";
    private static final String MY_ACCOUNT_PAGE_HEADER = "MY ACCOUNT";
    private static final String PAGE_DESCRIPTION = "Welcome to your account. Here you can manage all of your personal information and orders.";

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPage = new MainPage(driver, wait);
        driver.get("http://automationpractice.com/index.php");
    }

    @BeforeEach
    public void refreshPage() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public void finish() {
        driver.manage().deleteAllCookies();
        driver.close();
    }

    @Test
    @Order(2)
    @DisplayName("Вход с неверным паролем")
    public void incorrectPasswordSignInTest() {
        String errorAuthMessage = mainPage.clickSignInButton()
                                          .inputSignInEmail(USER_EMAIL)
                                          .inputSignInPassword(INCORRECT_USER_PASSWORD)
                                          .clickSubmitSignInButtonExpectedErrors()
                                          .getErrorAuthMessage();

        assertEquals(INCORRECT_PASSWORD_MESSAGE, errorAuthMessage,
                "Отсутсвует сообщение о неправильном пароле");
    }

    @Test
    @Order(3)
    @DisplayName("Вход с неверным логином")
    public void incorrectLoginSignInTest() {
        String errorAuthMessage = mainPage.clickSignInButton()
                                          .inputSignInEmail(INCORRECT_EMAIL)
                                          .inputSignInPassword(USER_PASSWORD)
                                          .clickSubmitSignInButtonExpectedErrors()
                                          .getErrorAuthMessage();

        assertEquals(INCORRECT_LOGIN_MESSAGE, errorAuthMessage,
                "Отсутсвует сообщение о неправильном логине");
    }

    @Test
    @Order(1)
    @DisplayName("Проверка валидации email")
    public void invalidEmailSignInTest() {
        boolean invalidEmailInputIsDisplayed = mainPage.clickSignInButton()
                                                       .inputSignInEmail(INVALID_EMAIL)
                                                       .inputSignInPassword(USER_PASSWORD)
                                                       .invalidEmailInputIsDisplayed();

        assertTrue(invalidEmailInputIsDisplayed, "Отсутствует валидация email");
    }

    @Test
    @DisplayName("Успешный вход под существующим пользователем")
    public void signInTest() {
         myAccountPage = mainPage.clickSignInButton()
                                           .inputSignInEmail(USER_EMAIL)
                                           .inputSignInPassword(USER_PASSWORD)
                                           .clickSubmitSignInButtonExpectedSuccess();

         assertAll(
                 () -> assertEquals(USER_NAME, myAccountPage.getAccountHeaderText(),
                         "Вход не выполнен или выполнен под другим пользователем"),
                 () -> assertEquals(MY_ACCOUNT_PAGE_HEADER, myAccountPage.getPageHeaderText(),
                         "Страница с аккаунтом не открылась"),
                 () -> assertEquals(PAGE_DESCRIPTION, myAccountPage.getPageDescription(),
                         "Описание страницы некорректное")
         );
    }
}
