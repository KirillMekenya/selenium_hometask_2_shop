package utils;

import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ActionsOnFailureExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {
        boolean testFailed = context.getExecutionException().isPresent();
        if (testFailed) {
            try {
                Object clazz = context.getRequiredTestInstance();
                Method takeScreenshot = clazz.getClass().getMethod("takeScreenshot");
                Method takeLogs = clazz.getClass().getMethod("saveLogs");
                takeScreenshot.setAccessible(true);
                takeLogs.setAccessible(true);
                takeScreenshot.invoke(clazz);
                takeLogs.invoke(clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}