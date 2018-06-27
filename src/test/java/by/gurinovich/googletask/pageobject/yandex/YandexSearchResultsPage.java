package by.gurinovich.googletask.pageobject.yandex;

import by.gurinovich.googletask.pageobject.AbstractResultPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YandexSearchResultsPage extends AbstractResultPage {

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
    public String getLinkURL(int i) {
        return searchResults.get(i).getAttribute("href");
    }

    @Override
    public String getLinkText(int link) {
        return searchResults.get(link).getText();
    }
}
