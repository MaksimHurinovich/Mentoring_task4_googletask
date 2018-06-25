package by.gurinovich.googletask.pageobject;

import org.openqa.selenium.WebElement;

public interface ResultPage {

    int searchResultsSize();

    WebElement getLink(int i);

    String getLinkText(WebElement link);
}
