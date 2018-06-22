package by.gurinovich.googletask.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {

    private static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();


    public static void createDriver(WebDriver driver) {
        drivers.set(driver);
    }

    public static WebDriver getDriver(){
        return drivers.get();
    }

    public static WebDriver getDriverInstance(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        return new ChromeDriver();
    }
}
