package pages;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class SearchResultPage extends BasePage {

    private final By blouseSearchResult = By.xpath("//a[@title='Blouse']/img");

    public SearchResultPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    public DressDetailPage clickBlouseItem(){
        driver.findElement(blouseSearchResult).click();
        return new DressDetailPage(driver, wait);
    }

}
