package by.gurinovich.googletask.pageobject.google;

import by.gurinovich.googletask.pageobject.AbstractMainPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class GoogleMainPage extends AbstractMainPage {

    private final String GOOGLE_URL = "https://google.com/";

    @FindBy(xpath = "//input[@id='lst-ib']")
    private WebElement searchTextField;

    @FindBy(xpath = "//div[@role='option']")
    private List<WebElement> variations;

    @FindBy(name = "btnK")
    private WebElement searchButton;

    @FindBy(xpath = "//*[contains(text(),'русский')]")
    private WebElement russianButton;

    public GoogleMainPage(WebDriver driver) {
        super(driver);
        driver.get(GOOGLE_URL);
    }

    public void typeWrongRequest(String request) {
        searchTextField.clear();
        searchTextField.sendKeys(request);
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(2, SECONDS)
                .pollingEvery(300, MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        wait.until(webDriver -> {
            boolean flag = false;
            try {
                for (WebElement variation : variations) {
                    flag = variation.isDisplayed();
                }
            } catch (StaleElementReferenceException e) {
                flag = false;
            }
            return !flag;
        });
    }

    public int getVariationsCount() {
        int count = 0;
        for (WebElement variation : variations) {
            if (variation.isDisplayed()) {
                count++;
            }
        }
        return count;
    }

    public void clearField() {
        searchTextField.clear();
    }

    @Override
    public void doSearch(String request) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(3, SECONDS)
                .pollingEvery(300, MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);
        wait.until(webDriver -> searchTextField.isEnabled());
        searchTextField.sendKeys(request);
        searchTextField.sendKeys(Keys.ENTER);
    }

    @Override
    public void navigateToMain() {
        driver.get(GOOGLE_URL);
    }

    public void toRussian() {
        russianButton.click();
    }


}
