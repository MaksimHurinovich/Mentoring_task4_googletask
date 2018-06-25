package by.gurinovich.googletask.core.listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

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
