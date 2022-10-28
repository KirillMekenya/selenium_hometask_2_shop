package pages;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class MainPage extends BasePage{

    public MainPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }
}
