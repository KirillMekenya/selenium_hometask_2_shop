package pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class BasePage {

    WebDriverWait wait;

    WebDriver driver;

    private final By searchInputField = By.id("search_query_top");
    private final By submitSearchBtn = By.name("submit_search");
    private final By cartBtn = By.cssSelector(".shopping_cart>a");
    private final By accHeader = By.cssSelector(".account > span");
    private final By signInBtn = By.cssSelector("a.login");

    public BasePage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Ввести в поисковую строку {searchString}")
    public BasePage inputSearchField(String searchString) {
        driver.findElement(searchInputField).click();
        driver.findElement(searchInputField).sendKeys(searchString);
        return this;
    }

    @Step("Нажать на кнопку Поиск")
    public SearchResultPage clickSearchButton() {
        driver.findElement(submitSearchBtn).click();
        return new SearchResultPage(driver, wait);
    }

    @Step("Нажать на кнопку Корзина")
    public CartPage clickCartButton() {
        driver.findElement(cartBtn).click();
        return new CartPage(driver, wait);
    }

    @Step("Нажать на кнопку Sign in")
    public SignInPage clickSignInButton() {
        driver.findElement(signInBtn).click();
        return new SignInPage(driver, wait);
    }

    @Step("Получить имя учётной записи")
    public String getAccountHeaderText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accHeader));
        return driver.findElement(accHeader).getText();
    }
}
