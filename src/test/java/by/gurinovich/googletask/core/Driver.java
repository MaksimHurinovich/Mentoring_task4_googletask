package by.gurinovich.googletask.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Driver {

    private static final String DEFAULT_WEB_DRIVER = "DEFAULT_WEB_DRIVER";

    private static DriverType defaultDriverType = DriverType.CHROME;

    private static HashMap<String, WebDriver> instances;

    static {
        instances = new HashMap<>();
    }

    public static WebDriver getWebDriverInstance(String name, DriverType type) throws UnknownDriverException {
        WebDriver driver;
        if (!instances.containsKey(name)) {
            switch (type) {
                case CHROME: {
                    System.setProperty("webdriver.chrome.driver",  "src/test/resources/chromedriver");
                    driver = new ChromeDriver(new ChromeOptions().addArguments("startMaximized"));
                    break;
                }
                default:
                    throw new UnknownDriverException("Unknown web driver specified: " + type);
            }
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            instances.put(name, driver);
        } else {
            driver = instances.get(name);
        }
        return driver;
    }

    public static WebDriver getWebDriverInstance(String name) throws UnknownDriverException {
        return getWebDriverInstance(name, defaultDriverType);
    }

    public static WebDriver getWebDriverInstance() throws UnknownDriverException {
        return getWebDriverInstance(DEFAULT_WEB_DRIVER, defaultDriverType);
    }
}
