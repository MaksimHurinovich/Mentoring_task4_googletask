package by.gurinovich.googletask.tests;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobjects.GoogleMainPage;
import by.gurinovich.googletask.pageobjects.GoogleSearchResultsPage;
import by.gurinovich.googletask.util.JsonConverter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GoogleTest {

    private GoogleMainPage mainPage;
    private GoogleSearchResultsPage resultsPage;

    @BeforeClass
    public void init(){
        mainPage = new GoogleMainPage(Driver.createDriver());
        mainPage.toRussian();
    }

    @AfterClass
    public void destroy(){
        mainPage.getDriver().quit();
    }
    @Test
    public void autocompleteAppearanceTest() throws FileNotFoundException {
        ArrayList<String> requests = JsonConverter.getRequests("google");
        for (String request : requests) {
            mainPage.typeCorrectRequest(request);
            Assert.assertNotEquals(mainPage.getVariationsCount(), 0, "ERROR: autocomplete on request '" + request + "' is wrong");
            mainPage.clearField();
        }
    }

    @Test
    public void autocompleteNotAppearsTest() throws FileNotFoundException {
        ArrayList<String> wrongRequests = JsonConverter.getWrongRequests("google");
        for (String request : wrongRequests) {
            mainPage.typeWrongRequest(request);
            Assert.assertEquals(mainPage.getVariationsCount(), 0, "ERROR: autocomplete on request '" + request + "' is wrong");
            mainPage.clearField();
        }
    }
    
}
