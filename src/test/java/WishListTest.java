import pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import pages.WishListsPage;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishListTest {

    private WebDriver driver;
    private MyAccountPage myAccountPage;
    private WishListsPage  wishListsPage;
    private static final String USER_EMAIL = "mekenya93@gmail.com";
    private static final String USER_PASSWORD = "561337";
    private static final String WISHES_PAGE_HEADER = "MY WISHLISTS";
    private static final String WISH = "Blouse";
    private static final String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        MainPage mainPage = new MainPage(driver, wait);
        driver.get("http://automationpractice.com/index.php");
        myAccountPage = mainPage.clickSignInButton()
                                .inputSignInEmail(USER_EMAIL)
                                .inputSignInPassword(USER_PASSWORD)
                                .clickSubmitSignInButtonExpectedSuccess();
    }

    @AfterAll
    public void finish() {
        driver.manage().deleteAllCookies();
        driver.close();
    }

    @Test
    @DisplayName("Проверка открытия страницы")
    @Order(1)
    public void wishPageOpening() {
        wishListsPage = myAccountPage.clickWhishlistsButton();

        assertEquals(WISHES_PAGE_HEADER, wishListsPage.getPageHeaderText(),
                "Страница с жеоаниями не открыта или хидер некорректный");
    }

    @Test
    @DisplayName("Создания желания")
    @Order(2)
    public void wishCreation() {
        String wishName = wishListsPage.inputWish(WISH).clickSubmitWishButton().getWishName();
        String wishDate = wishListsPage.getWishDateCreation();


        assertAll(
                () -> assertEquals(WISH, wishName, "Создалось некорректное желание"),
                () -> assertEquals(CURRENT_DATE, wishDate, "Некорректная дата создания")
        );
    }

    @Test
    @DisplayName("Удаление желания, отклонение алерта")
    @Order(3)
    public void wishRemovingDismissAlert() {
        boolean isRemoved = wishListsPage.clickRemoveWishButton()
                                         .declineAlert()
                                         .checkIfWishIsNotRemoved();

        assertTrue(isRemoved, "Желание  удалено");
    }

    @Test
    @DisplayName("Удаление желания, подтверждение алерта")
    @Order(4)
    public void wishRemovingAcceptAlert() {
        boolean isRemoved = wishListsPage.clickRemoveWishButton()
                                         .acceptAlert()
                                         .checkIfWishIsRemoved();

        assertTrue(isRemoved, "Желание не удалено");
    }
}
