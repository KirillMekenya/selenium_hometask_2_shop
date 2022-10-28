package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class AddToCartWindow extends BasePage {

    private final By addToCartSuccessHeader = By.cssSelector("h2:nth-child(2)");
    private final By continueShoppingBtn = By.cssSelector(".continue > span");

    public AddToCartWindow(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getAddToCartSuccessMessage () {
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartSuccessHeader));
        return driver.findElement(addToCartSuccessHeader).getText();
    }

    public SearchResultPage clickContinueShoppingButton() {
        driver.findElement(continueShoppingBtn).click();
        return new SearchResultPage(driver, wait);
    }
}
