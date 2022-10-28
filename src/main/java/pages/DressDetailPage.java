package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class DressDetailPage extends BasePage{

    private final By addToCartBtn = By.xpath("//span[text()='Add to cart']");

    public DressDetailPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public AddToCartWindow cliclAddToCartButton(){
        driver.findElement(addToCartBtn).click();
        return new AddToCartWindow(driver, wait);
    }
}