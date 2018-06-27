package by.gurinovich.googletask.core.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static Logger LOGGER = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LOGGER.info("Test " + iTestResult.getName() + " started.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.debug("Test " + iTestResult.getName() + " succeed in "
                + (iTestResult.getEndMillis() - iTestResult.getStartMillis()) + " millis.");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOGGER.debug("Test" + iTestResult.getName() + " failed. ");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.debug("Test " + iTestResult.getName() + " skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        LOGGER.debug("Test " + iTestResult.getName() + " failed within success percentage.");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        LOGGER.info(iTestContext.getName() + " started.");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.info(iTestContext.getName() + " finished.");
    }
}
