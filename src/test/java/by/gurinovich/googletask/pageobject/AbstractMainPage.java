package by.gurinovich.googletask.pageobject;

import org.openqa.selenium.WebDriver;

public abstract class AbstractMainPage extends AbstractPage {

    public AbstractMainPage(WebDriver driver) {
        super(driver);
    }

    public abstract void doSearch(String request);

    public abstract void navigateToMain();
}
