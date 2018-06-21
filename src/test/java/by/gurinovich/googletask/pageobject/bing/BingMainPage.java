package by.gurinovich.googletask.pageobject.bing;

import by.gurinovich.googletask.pageobject.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        searchField.sendKeys(request);
        searchBtn.click();
    }

    public void navigateToMain() {
        driver.get(BING_URL);
    }


}
