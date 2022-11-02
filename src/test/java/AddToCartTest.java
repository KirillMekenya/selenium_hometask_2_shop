import org.junit.jupiter.api.extension.RegisterExtension;
import pages.AddToCartWindow;
import pages.CartPage;
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
import utils.AllureWatcher;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddToCartTest {

    private final WebDriver driver = new ChromeDriver();

    private MainPage mainPage;
    private AddToCartWindow addToCartWindow;
    private CartPage cartPage;

    private static final String SUCCESS_ADDING_TO_CART_MESSAGE = "Product successfully added to your shopping cart";
    private static final String GOOD_NAME = "Blouse";
    private static final String TOTAL_CART_PRICE = "$29.00";

    @RegisterExtension
    AllureWatcher watcher = new AllureWatcher(this.driver, "target/surefire-reports");

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
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
    @Order(1)
    @DisplayName("Проверка добавления в корзину")
    public void addToCartTest() {
        addToCartWindow = mainPage.inputSearchField(GOOD_NAME)
                                  .clickSearchButton()
                                  .clickBlouseItem()
                                  .cliclAddToCartButton();
        assertEquals(SUCCESS_ADDING_TO_CART_MESSAGE, addToCartWindow.getAddToCartSuccessMessage(),
                "Товар не добавлен в корзину либо сообщение об успехе некорректно");
    }

    @Test
    @Order(2)
    @DisplayName("Проверка что продукт добавлен в корзину")
    public void checkProductInCart() {
        cartPage = addToCartWindow.clickContinueShoppingButton()
                                  .clickCartButton();

        assertEquals(GOOD_NAME, cartPage.getProductDescriptionText(),
                "Не тот товар в корзине, название товара некорректно");
    }

    @Test
    @Order(3)
    @DisplayName("Проверка итоговой суммы в корзине")
    public void checkSumInCart() {
        assertEquals(TOTAL_CART_PRICE, cartPage.getTotalSumOfCart(), "Некорректная сумма в корзине");
    }
}
