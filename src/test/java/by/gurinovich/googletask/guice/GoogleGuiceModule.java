package by.gurinovich.googletask.guice;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.google.GoogleMainPage;
import by.gurinovich.googletask.pageobject.google.GoogleSearchResultsPage;
import com.google.inject.AbstractModule;
import org.openqa.selenium.WebDriver;

public class GoogleGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        WebDriver webDriver = Driver.getDriverInstance();
        Driver.createDriver(webDriver);
        bind(GoogleMainPage.class).toInstance(new GoogleMainPage(Driver.getDriver()));
        bind(GoogleSearchResultsPage.class).toInstance(new GoogleSearchResultsPage(Driver.getDriver()));
    }
}
