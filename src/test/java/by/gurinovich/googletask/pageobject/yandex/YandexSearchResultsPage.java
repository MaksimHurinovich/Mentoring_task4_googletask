package by.gurinovich.googletask.pageobject.yandex;

import by.gurinovich.googletask.pageobject.AbstractPage;
import by.gurinovich.googletask.pageobject.ResultPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YandexSearchResultsPage extends AbstractPage implements ResultPage {

    @FindBy(xpath = "//li[@class = 'serp-item']//h2/a")
    private List<WebElement> searchResults;

    public YandexSearchResultsPage(WebDriver driver) {
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
    public String getLinkText(WebElement result) {
        return result.getText();
    }
}
