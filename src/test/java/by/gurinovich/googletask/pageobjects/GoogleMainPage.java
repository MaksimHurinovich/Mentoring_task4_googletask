package by.gurinovich.googletask.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleMainPage extends AbstractPage {

    private final String GOOGLE_URL = "https://google.com/";

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[8]/span[1]/center[1]/div[1]/div[1]/a[1]/img[1]")
    private WebElement googleLogo;

    @FindBy(xpath = "//input[@id='lst-ib']")
    private WebElement searchTextField;

    @FindBy(xpath = "//div[@role='option']")
    private List<WebElement> variations;

    @FindBy(name = "btnK")
    private WebElement searchButton;

    @FindBy(xpath = "//a[@id='fsettl']")
    private WebElement settings;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[8]/div[1]/div[1]/div[1]/div[1]/div[1]/span[1]/span[1]/span[1]/a[1]")
    private WebElement preferences;

    @FindBy(xpath = "//a[@id='regionanchormore']")
    private WebElement regionMore;

    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[8]/span[1]/center[1]/div[3]/div[1]/div[1]/a[1]")
    private WebElement russianButton;

    @FindBy(xpath = "/html[1]/body[1]/div[5]/form[1]/div[1]/div[2]/div[2]/div[1]/div[1]")
    private WebElement savePreferences;

    public GoogleMainPage(WebDriver driver) {
        super(driver);
        driver.get(GOOGLE_URL);
    }

    public void typeWrongRequest(String request) {
        searchTextField.sendKeys(request);
        new WebDriverWait(driver, 2).until(ExpectedConditions.invisibilityOfAllElements(variations));
    }

    public void typeCorrectRequest(String request) {
        searchTextField.sendKeys(request);
        new WebDriverWait(driver, 2).until(ExpectedConditions.visibilityOfAllElements(variations));
    }

    public void typeRequest(String request) {
        searchTextField.sendKeys(request);
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

    public void doSearch() {
        searchTextField.sendKeys(Keys.ENTER);
    }

    public void toRussian() {
        russianButton.click();
    }



}
