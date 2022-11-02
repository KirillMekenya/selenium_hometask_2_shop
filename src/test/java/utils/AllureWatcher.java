package utils;

import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class AllureWatcher implements TestWatcher {

    static WebDriver driver;
    String path;

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {

    }

    public AllureWatcher() {
    }

    public AllureWatcher(WebDriver driver, String path) {
        AllureWatcher.driver = driver;
        this.path = path;
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable throwable) {
        // do something
    }


    @SneakyThrows
    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        captureScreenshot(driver);
        extractBrowserLogs(driver);
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        // do something
    }

    public void captureScreenshot(WebDriver driver) throws IOException {
        File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Allure.addAttachment("Screenshot", FileUtils.openInputStream(screenshotAs));
    }

    public void extractBrowserLogs(WebDriver driver) {
        Allure.addAttachment("Console log: ", String.valueOf(driver.manage().logs().get(LogType.BROWSER).getAll()));
    }
}
