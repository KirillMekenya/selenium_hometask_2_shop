package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage{

    private final By pageHeader = By.cssSelector(".page-heading");
    private final By pageDescription = By.cssSelector("p.info-account");
    private final By wishlistsButton = By.xpath("//span[text()='My wishlists']");

    public MyAccountPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Получить текст названия страницы My account")
    public String getPageHeaderText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
        return driver.findElement(pageHeader).getText();
    }

    public WishListsPage clickWhishlistsButton() {
        driver.findElement(wishlistsButton).click();
        return new WishListsPage(driver, wait);
    }

    public String getPageDescription() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageDescription));
        return driver.findElement(pageDescription).getText();
    }
}
