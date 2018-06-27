package by.gurinovich.googletask.test.desktop.bing;

import by.gurinovich.googletask.guice.BingGuiceModule;
import by.gurinovich.googletask.httpclient.HttpClient;
import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import by.gurinovich.googletask.pageobject.bing.BingMainPage;
import by.gurinovich.googletask.pageobject.bing.BingSearchResultsPage;
import by.gurinovich.googletask.util.JsonRequestsManager;
import by.gurinovich.googletask.util.TextUtil;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Guice(modules = BingGuiceModule.class)
public class BingTest {

    @Inject
    private BingMainPage mainPage;
    @Inject
    private BingSearchResultsPage resultsPage;

    @Test(dataProvider = "bingDP")
    public void linkContainsRequestTest(String request) {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        List<String> wordsInRequest = TextUtil.textToWords(request);
        for (int i = 0; i < resultsPage.searchResultsSize() - 1; i++) {
            List<String> linkTextWords = TextUtil.textToWords(resultsPage.getLinkText(i));
            softAssert.assertFalse(Collections.disjoint(wordsInRequest, linkTextWords), "ERROR in link#" + i + ": " + wordsInRequest + " " + linkTextWords);
        }
        mainPage.navigateToMain();
        softAssert.assertAll();
    }

    @Test(dataProvider = "wrongDP")
    public void nothingFoundTest(String request) {
        mainPage.doSearch(request);
        int resultsSize = resultsPage.searchResultsSize();
        mainPage.navigateToMain();
        Assert.assertEquals(resultsSize, 0, "ERROR: in bad request " + request);
    }

    @Test(dataProvider = "bingDP")
    public void checkStatusCodeTest(String request) {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        HttpClient client = new HttpClient();
        for(int i = 0; i < resultsPage.searchResultsSize() - 3; i++){
            HttpResponse response = client.get(resultsPage.getLinkURL(i));
            softAssert.assertEquals(response.getStatusCode(), 200, "Link#" + i + " has wrong status code,");
        }
        mainPage.navigateToMain();
        softAssert.assertAll();
    }

    @DataProvider(name = "bingDP")
    public Object[] requestsProvider() {
        ArrayList<String> requests = JsonRequestsManager.getRequests("bing");
        return requests.toArray();
    }

    @DataProvider(name = "wrongDP")
    public Object[] wrongProvider() {
        ArrayList<String> requests = JsonRequestsManager.getWrongRequests("bing");
        return requests.toArray();
    }
}
