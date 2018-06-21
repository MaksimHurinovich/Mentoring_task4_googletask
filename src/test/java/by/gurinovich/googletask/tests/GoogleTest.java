package by.gurinovich.googletask.tests;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobjects.GoogleMainPage;
import by.gurinovich.googletask.pageobjects.GoogleSearchResultsPage;
import by.gurinovich.googletask.util.JsonConverter;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleTest {

    private GoogleMainPage mainPage;
    private GoogleSearchResultsPage resultsPage;
    private SoftAssert softAssert;

    @BeforeClass
    public void init() {
        mainPage = new GoogleMainPage(Driver.createDriver());
        resultsPage = new GoogleSearchResultsPage(mainPage.getDriver());
        mainPage.toRussian();
        softAssert = new SoftAssert();
    }

    @AfterClass
    public void destroy() {
        mainPage.getDriver().quit();
    }

    @Test
    public void autocompleteAppearanceTest() {
        ArrayList<String> requests = JsonConverter.getRequests("google");
        for (String request : requests) {
            mainPage.typeCorrectRequest(request);
            softAssert.assertNotEquals(mainPage.getVariationsCount(), 0, "ERROR: autocomplete on request '" + request + "' is wrong");
            mainPage.clearField();
        }
        softAssert.assertAll();
    }

    @Test
    public void autocompleteNotAppearsTest() {
        ArrayList<String> wrongRequests = JsonConverter.getWrongRequests("google");
        for (String request : wrongRequests) {
            mainPage.typeWrongRequest(request);
            softAssert.assertEquals(mainPage.getVariationsCount(), 0, "ERROR: autocomplete on request '" + request + "' is wrong");
            mainPage.clearField();
        }
        softAssert.assertAll();
    }

    @Test
    public void mainPageTitleCheck() {
        Assert.assertEquals(mainPage.getDriver().getTitle(), "Google", "ERROR: Title is wrong.");
    }

    @Test(dataProvider = "googleDP")
    public void linkContainsRequestTest(String request) {
        mainPage.typeRequest(request);
        mainPage.doSearch();
        List<String> wordsInRequest = textToWords(request);
        for (int i = 0; i < resultsPage.resultListSize(); i++) {
            List<String> linkTextWords = textToWords(resultsPage.getLinkText(resultsPage.getLink(i)));
            softAssert.assertFalse(Collections.disjoint(wordsInRequest, linkTextWords), "ERROR in link#" + i);
        }
        resultsPage.navigateBack();
        softAssert.assertAll();
    }

    @DataProvider(name = "googleDP")
    public Object[][] googleDataProvider() {
        return new Object[][]{
                {
                        "xxxtentacion"
                },
                {
                        "mysql vs mongodb"
                },
                {
                        "earth is flat"
                }
        };
    }

    private List<String> textToWords(String text){
        return Arrays.asList(text.toUpperCase().replaceAll("[{}\\[\\](),.\"!?<>:;]", "").split(" "));
    }
}
