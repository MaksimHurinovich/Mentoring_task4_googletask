package by.gurinovich.googletask.cases;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.page_objects.GoogleMainPage;
import by.gurinovich.googletask.page_objects.GoogleSearchResultsPage;
import org.testng.annotations.BeforeClass;

public class GoogleTest {

    private GoogleMainPage mainPage;
    private GoogleSearchResultsPage resultsPage;

    @BeforeClass
    public void init(){
        mainPage = new GoogleMainPage(Driver.createDriver());
    }
}
