import elements.AddToCartWindow;
import elements.CartPage;
import elements.DressDetailPage;
import elements.MainPageElements;
import elements.SearchResultPageElements;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddToCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    MainPageElements mainPageElements;
    SearchResultPageElements searchResultPage;
    DressDetailPage dressDetailLocator;
    AddToCartWindow addToCartWindowL;
    CartPage cartPage;

    private static final String SUCCESS_ADDING_TO_CART_MESSAGE = "Product successfully added to your shopping cart";
    private static final String GOOD_NAME = "Blouse";
    private static final String TOTAL_CART_PRICE = "$29.00";

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        mainPageElements = new MainPageElements();
        searchResultPage = new SearchResultPageElements();
        dressDetailLocator = new DressDetailPage();
        addToCartWindowL = new AddToCartWindow();
        cartPage = new CartPage();
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
        WebElement searchInputField = driver.findElement(mainPageElements.getSearchInputField());
        WebElement searchBtn = driver.findElement(mainPageElements.getSubmitSearchBtn());
        searchInputField.click();
        searchInputField.sendKeys("Blouse");
        searchBtn.click();
        WebElement blouseSrchResult = driver.findElement(searchResultPage.getBlouseSearchResult());
        blouseSrchResult.click();
        WebElement addToCartBtn = driver.findElement(dressDetailLocator.getAddToCartBtn());
        addToCartBtn.click();
        WebElement succesfullMessage = driver.findElement(addToCartWindowL.getAddToCartSuccessHeader());
        wait.until(ExpectedConditions.visibilityOf(succesfullMessage));
        assertEquals(SUCCESS_ADDING_TO_CART_MESSAGE, succesfullMessage.getText(),
                "Товар не добавлен в корзину либо сообщение об успехе некорректно");
    }

    @Test
    @Order(2)
    @DisplayName("Проверка что продукт добавлен в корзину")
    public void checkProductInCart() {
        WebElement continueShoppingBtn = driver.findElement(addToCartWindowL.getContinueShoppingBtn());
        continueShoppingBtn.click();
        WebElement cartButton = driver.findElement(mainPageElements.getCartBtn());
        cartButton.click();
        WebElement blouseDescr = driver.findElement(cartPage.getProductNameLocator());
        wait.until(ExpectedConditions.visibilityOf(blouseDescr));
        assertEquals(GOOD_NAME, blouseDescr.getText(), "Не тот товар в корзине, название товара некорректно");
    }

    @Test
    @Order(3)
    @DisplayName("Проверка итоговой суммы в корзине")
    public void checkSumInCart() {
        WebElement totalPrice = driver.findElement(cartPage.getTotalPriceLocator());
        wait.until(ExpectedConditions.visibilityOf(totalPrice));
        assertEquals(TOTAL_CART_PRICE, totalPrice.getText(), "Некорректная сумма в корзине");
    }
}
