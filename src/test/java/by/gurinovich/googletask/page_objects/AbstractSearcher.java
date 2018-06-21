package by.gurinovich.googletask.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class AbstractSearcher {

    protected WebDriver driver;

    public AbstractSearcher(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver()
    {
        return this.driver;
    }
}
