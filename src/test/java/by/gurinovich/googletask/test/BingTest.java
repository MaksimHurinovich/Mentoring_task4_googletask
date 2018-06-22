package by.gurinovich.googletask.test;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.bing.BingMainPage;
import by.gurinovich.googletask.pageobject.bing.BingSearchResultsPage;
import by.gurinovich.googletask.util.JsonConverter;
import by.gurinovich.googletask.util.TextUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BingTest {

    private BingMainPage mainPage;
    private BingSearchResultsPage resultsPage;

    @BeforeClass
    public void init() {
        mainPage = new BingMainPage(Driver.getDriver());
        resultsPage = new BingSearchResultsPage(mainPage.getDriver());
    }

    @AfterClass
    public void destroy() {
        mainPage.getDriver().quit();
    }

    @Test(dataProvider = "bingDP")
    public void LinkContainsRequestTest(String request){
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        List<String> wordsInRequest = TextUtil.textToWords(request);
        for (int i = 0; i < resultsPage.searchResultsSize(); i++) {
            List<String> linkTextWords = TextUtil.textToWords(resultsPage.getLinkText(resultsPage.getResult(i)));
            softAssert.assertFalse(Collections.disjoint(wordsInRequest, linkTextWords), "ERROR in link#" + i + ": " + wordsInRequest + " " + linkTextWords);
        }
        mainPage.navigateToMain();
        softAssert.assertAll();
    }

    @DataProvider(name = "bingDP")
    public Object[] requestsProvider(){
        ArrayList<String> requests = JsonConverter.getRequests("bing");
        return requests.toArray();
    }
}
