package elements;

import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class DressDetailPage {

    MainPageElements mainPageElements;

    private final By addToCartBtn = By.xpath("//span[text()='Add to cart']");
}
