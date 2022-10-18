package elements;

import lombok.Getter;
import org.openqa.selenium.By;

@Getter
public class SignInPageElements extends MainPageElements {
    private final By emailInputField = By.id("email");
    private final By passwordInputField = By.id("passwd");
    private final By submitButton = By.xpath("//button[@id='SubmitLogin']/span");
    private final By emailCreateUser = By.id("email_create");
    private final By submitCreateUserBtn = By.id("SubmitCreate");
    private final By errorMessage = By.xpath("//div[@id='create_account_error']/ol/li");
}
