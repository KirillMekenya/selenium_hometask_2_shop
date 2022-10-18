package elements;

import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class SearchResultPageElements extends MainPageElements {

    private final By blouseSearchResult = By.xpath("//a[@title='Blouse']/img");

}
