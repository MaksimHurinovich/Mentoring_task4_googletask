package by.gurinovich.googletask.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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
        driver.navigate().to(GOOGLE_URL);
    }

    public void typeRequest(String request){
        searchTextField.sendKeys(request);
        new WebDriverWait(driver, 3);
    }

    public int getVariationsCount(){
        int count = 0;
        for(WebElement variation: variations){
            if(variation.isDisplayed()){
                count++;
            }
        }
        return count;
    }

    public void clearField(){
        searchTextField.clear();
    }

    public void doSearch(){
        searchButton.click();
    }

    public void toRussian(){
        russianButton.click();
    }

}
