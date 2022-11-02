package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class CartPage extends BasePage {

    private final By productNameLocator = By.xpath("//tr//p[@class='product-name']/a");
    private final By totalPriceLocator = By.id("total_price");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Получить текст описания продукта")
    public String getProductDescriptionText() {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(productNameLocator)));
        return driver.findElement(productNameLocator).getText();
    }

    @Step("Получить общую сумму корзины")
    public String getTotalSumOfCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalPriceLocator));
        return driver.findElement(totalPriceLocator).getText();
    }
}
