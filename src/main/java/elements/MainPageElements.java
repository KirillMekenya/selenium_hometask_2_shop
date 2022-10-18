package elements;

import lombok.Getter;
import org.openqa.selenium.By;
@Getter
public class MainPageElements {

    private final By searchInputField = By.id("search_query_top");
    private final By submitSearchBtn = By.name("submit_search");
    private final By cartBtn = By.cssSelector(".shopping_cart>a");
    private final By accHeader = By.cssSelector(".account > span");
    private final By signInBtn = By.cssSelector("a.login");
}
