package by.gurinovich.googletask.pageobject.bing;

import by.gurinovich.googletask.pageobject.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;


public class BingMainPage extends AbstractPage {

    private final String BING_URL = "https://bing.com/";

    @FindBy(xpath = "//input[@id='sb_form_q']")
    private WebElement searchField;

    @FindBy(xpath = "//input[@id='sb_form_go']")
    private WebElement searchBtn;


    public BingMainPage(WebDriver driver) {
        super(driver);
        driver.get(BING_URL);
    }

    public void doSearch(String request) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(3, SECONDS)
                .pollingEvery(300, MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        wait.until((ExpectedCondition<Boolean>) webDriver -> searchField.isDisplayed());
        searchField.clear();
        searchField.sendKeys(request);
        searchBtn.click();
    }

    public void navigateToMain() {
        driver.get(BING_URL);
    }


}
