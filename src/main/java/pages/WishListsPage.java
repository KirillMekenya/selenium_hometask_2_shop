package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WishListsPage extends BasePage {

    private final By pageHeader = By.cssSelector("h1.page-heading");
    private final By newWishInputField = By.cssSelector("input#name");
    private final By submitWishButton = By.cssSelector("button#submitWishlist");
    private final By wishName = By.xpath("//tbody/tr/td[1]/a");
    private final By wishDateCreation = By.xpath("//tbody/tr/td[4]");
    private final By removeWishButton = By.cssSelector("i.icon-remove");

    public WishListsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getPageHeaderText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
        return driver.findElement(pageHeader).getText();
    }

    public String getWishName() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(wishName));
        return driver.findElement(wishName).getText();
    }

    public String getWishDateCreation() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(wishDateCreation));
        return driver.findElement(wishDateCreation).getText();
    }

    public WishListsPage inputWish(String wish) {
        driver.findElement(newWishInputField).click();
        driver.findElement(newWishInputField).sendKeys(wish);
        return this;
    }

    public WishListsPage clickSubmitWishButton() {
        driver.findElement(submitWishButton).click();
        return this;
    }

    public WishListsPage acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        return this;
    }

    public WishListsPage declineAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
        return this;
    }

    public boolean checkIfWishIsRemoved() {
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(wishName)));
        return driver.findElements(wishName).size() == 0;
    }

    public boolean checkIfWishIsNotRemoved() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(wishName));
        return driver.findElements(wishName).size() > 0;
    }

    public WishListsPage clickRemoveWishButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeWishButton));
        driver.findElement(removeWishButton).click();
        return this;
    }
}
