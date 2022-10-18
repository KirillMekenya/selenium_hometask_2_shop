package elements;

import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class CartPage extends MainPageElements{
    private final By productNameLocator = By.xpath("//tr//p[@class='product-name']/a");
    private final By totalPriceLocator = By.id("total_price");

}
