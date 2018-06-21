package by.gurinovich.googletask.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GoogleSearchResultsPage extends AbstractPage {

    private final String GOOGLE_URL = "https://google.com/";

    @FindBy(xpath = "//h3[@class='r']//a")
    private List<WebElement> resultList;

    @FindBy(xpath = "//input[@id='lst-ib']")
    private WebElement inputField;

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateBack() {
        driver.get(GOOGLE_URL);
    }

    public String getRequest(){
        return inputField.getText();
    }

    public String getLinkText(WebElement link){
        return link.getText();
    }

    public WebElement getLink(int i){
        return resultList.get(i);
    }

    public int resultListSize(){
        return resultList.size();
    }
}
