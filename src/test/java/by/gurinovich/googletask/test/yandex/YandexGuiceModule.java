package by.gurinovich.googletask.test.yandex;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.yandex.YandexMainPage;
import by.gurinovich.googletask.pageobject.yandex.YandexSearchResultsPage;
import com.google.inject.AbstractModule;
import org.openqa.selenium.WebDriver;

public class YandexGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        WebDriver webDriver = Driver.getDriverInstance();
        Driver.createDriver(webDriver);
        bind(YandexMainPage.class).toInstance(new YandexMainPage(Driver.getDriver()));
        bind(YandexSearchResultsPage.class).toInstance(new YandexSearchResultsPage(Driver.getDriver()));
    }
}
