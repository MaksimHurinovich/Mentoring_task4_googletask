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

    public static WebDriver createDriver(){
        System.setProperty("webdriver.chrome.driver",  "src/test/resources/chromedriver");
        return new ChromeDriver(new ChromeOptions().addArguments("startMaximized"));
    }
}
