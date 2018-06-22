package by.gurinovich.googletask.core.listener;

import by.gurinovich.googletask.core.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestListener implements IClassListener, ITestListener {

    @Override
    public void onBeforeClass(ITestClass iTestClass) {
        WebDriver webDriver = Driver.getDriverInstance();
        Driver.createDriver(webDriver);
    }

    @Override
    public void onAfterClass(ITestClass iTestClass) {
        Driver.getDriver().quit();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getName() + " started.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getName() + " succeed in "
                + (iTestResult.getEndMillis()- iTestResult.getStartMillis()) + " millis.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Test" + iTestResult.getName() + " failed. ");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getName() + " skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test " + iTestResult.getName() + " failed within success percentage.");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println(iTestContext.getName() + " started.");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println(iTestContext.getName() + " finished.");
    }
}
