package by.gurinovich.googletask.pageobject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GoogleMainPage extends AbstractPage {

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
