package by.gurinovich.googletask.pageobject.google;

import by.gurinovich.googletask.pageobject.AbstractPage;
import by.gurinovich.googletask.pageobject.ResultPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GoogleSearchResultsPage extends AbstractPage implements ResultPage {

    private final String GOOGLE_URL = "https://google.com/";

    @FindBy(xpath = "//div[@class='rc']/h3/a")
    private List<WebElement> resultList;

    public GoogleSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateBack() {
        driver.get(GOOGLE_URL);
    }

    @Override
    public String getLinkText(WebElement link) {
        return link.getText();
    }

    @Override
    public int searchResultsSize() {
        return resultList.size();
    }

    @Override
    public WebElement getLink(int i) {
        return resultList.get(i);
    }

}
