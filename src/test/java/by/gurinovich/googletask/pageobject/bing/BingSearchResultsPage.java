package by.gurinovich.googletask.pageobject.bing;

import by.gurinovich.googletask.pageobject.AbstractPage;
import by.gurinovich.googletask.pageobject.ResultPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BingSearchResultsPage extends AbstractPage implements ResultPage {

    @FindBy(xpath = "//ol[@id='b_results']/li/h2/a")
    private List<WebElement> searchResults;

    public BingSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public int searchResultsSize() {
        return searchResults.size();
    }

    @Override
    public WebElement getLink(int i) {
        return searchResults.get(i);
    }

    @Override
    public String getLinkText(WebElement link) {
        return link.getText();
    }
}
