package by.gurinovich.googletask.pageobject.yandex;

import by.gurinovich.googletask.pageobject.AbstractPage;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class YandexMainPage extends AbstractPage {

    private final String YANDEX_URL = "https://yandex.ru/";

    @FindBy(xpath = "//input[@id='text']")
    private WebElement searchTextField;

    @FindBy(xpath = "//div[@class='search2__button']")
    private WebElement searchBtn;


    public YandexMainPage(WebDriver driver) {
        super(driver);
        driver.get(YANDEX_URL);
    }

    public void doSearch(String request) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(3, SECONDS)
                .pollingEvery(500, MILLISECONDS)
                .ignoring(StaleElementReferenceException.class);
        wait.until((ExpectedCondition<Boolean>) webDriver -> searchTextField.isEnabled());
        searchTextField.clear();
        searchTextField.sendKeys(request);
        searchBtn.click();
    }

    public void navigateToMain() {
        driver.get(YANDEX_URL);
    }
}
