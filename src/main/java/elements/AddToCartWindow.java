package elements;

import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class AddToCartWindow extends MainPageElements{

    private final By addToCartSuccessHeader = By.cssSelector("h2:nth-child(2)");
    private final By continueShoppingBtn = By.cssSelector(".continue > span");

}
