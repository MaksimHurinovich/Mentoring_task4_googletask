package by.gurinovich.googletask.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class ResultPage extends AbstractPage {

    public ResultPage(WebDriver driver) {
        super(driver);
    }

    public abstract int searchResultsSize();

    public abstract String getLinkURL(int i);

    public abstract String getLinkText(int link);
}
