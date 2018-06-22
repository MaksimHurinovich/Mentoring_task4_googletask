package by.gurinovich.googletask.listener;

import by.gurinovich.googletask.core.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.IClassListener;
import org.testng.ITestClass;

public class TestListener implements IClassListener {

    @Override
    public void onBeforeClass(ITestClass iTestClass) {
        WebDriver webDriver = Driver.getDriverInstance();
        Driver.createDriver(webDriver);
    }

    @Override
    public void onAfterClass(ITestClass iTestClass) {
        Driver.getDriver().quit();
    }
}
