package by.gurinovich.googletask.guice;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.bing.BingMainPage;
import by.gurinovich.googletask.pageobject.bing.BingSearchResultsPage;
import com.google.inject.AbstractModule;
import org.openqa.selenium.WebDriver;

public class BingGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        WebDriver webDriver = Driver.getDriverInstance();
        Driver.createDriver(webDriver);
        bind(BingMainPage.class).toInstance(new BingMainPage(Driver.getDriver()));
        bind(BingSearchResultsPage.class).toInstance(new BingSearchResultsPage(Driver.getDriver()));
    }
}
