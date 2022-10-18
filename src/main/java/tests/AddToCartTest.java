package tests;

import elements.AddToCartWindow;
import elements.CartPage;
import elements.DressDetailPage;
import elements.MainPageElements;
import elements.SearchResultPageElements;
import exceptions.ValueDoesNotMatch;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddToCartTest {
    public static void main(String[] args) throws ValueDoesNotMatch {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("http://automationpractice.com/index.php");

        MainPageElements mainPageElements = new MainPageElements();
        SearchResultPageElements searchResultPage = new SearchResultPageElements();
        DressDetailPage dressDetailLocator = new DressDetailPage();
        AddToCartWindow addToCartWindowL = new AddToCartWindow();
        CartPage cartPage = new CartPage();


        try {
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
            if (!succesfullMessage.getText().contains("Product successfully added to your shopping cart")) {
                throw new ValueDoesNotMatch("Товар не добавлен в корзину либо сообщение об успехе некорректно");
            }
            WebElement continueShoppingBtn = driver.findElement(addToCartWindowL.getContinueShoppingBtn());
            continueShoppingBtn.click();
            WebElement cartButton = driver.findElement(mainPageElements.getCartBtn());
            cartButton.click();
            WebElement blouseDescr = driver.findElement(cartPage.getProductNameLocator());
            wait.until(ExpectedConditions.visibilityOf(blouseDescr));
            if (!blouseDescr.getText().equals("Blouse")) {
                throw new ValueDoesNotMatch("Не тот товар в корзине, название товара некорректно");
            }
            WebElement totalPrice = driver.findElement(cartPage.getTotalPriceLocator());
            wait.until(ExpectedConditions.visibilityOf(totalPrice));
            if (!totalPrice.getText().equals("$29.00")) {
                throw new ValueDoesNotMatch("Некорректная сумма в корзине");
            }
        } finally {
            driver.close();
        }
    }
}
