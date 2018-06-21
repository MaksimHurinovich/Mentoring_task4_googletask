package by.gurinovich.googletask.pageobject.yandex;

import by.gurinovich.googletask.pageobject.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class YandexSearchResultsPage extends AbstractPage {

    @FindBy(xpath = "//li[@class = 'serp-item']//h2/a")
    private List<WebElement> searchResults;


    public YandexSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public int searchResultsSize() {
        return searchResults.size();
    }

    public WebElement getResult(int i) {
        return searchResults.get(i);
    }

    public String getLinkText(WebElement result) {
        return result.getText();
    }
}
