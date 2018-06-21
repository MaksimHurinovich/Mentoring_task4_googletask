package by.gurinovich.googletask.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {

    public static WebDriver createDriver(){
        System.setProperty("webdriver.chrome.driver",  "src/test/resources/chromedriver");
        return new ChromeDriver(new ChromeOptions().addArguments("startMaximized"));
    }
}
