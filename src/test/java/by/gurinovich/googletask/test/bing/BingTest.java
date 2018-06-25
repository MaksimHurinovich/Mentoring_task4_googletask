package by.gurinovich.googletask.test.bing;

import by.gurinovich.googletask.pageobject.bing.BingMainPage;
import by.gurinovich.googletask.pageobject.bing.BingSearchResultsPage;
import by.gurinovich.googletask.util.JsonRequestsManager;
import by.gurinovich.googletask.util.TextUtil;
import com.google.inject.Inject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
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
            List<String> linkTextWords = TextUtil.textToWords(resultsPage.getLinkText(resultsPage.getResult(i)));
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
    public void checkStatusCodeTest(String request) throws IOException {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        CloseableHttpClient client = HttpClients.createDefault();
        for (int i = 0; i < 3; i++) {
            String url = resultsPage.getResult(i).getAttribute("href");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            softAssert.assertEquals(statusCode, 200, "ERROR: in link#" + i + " status code is " + statusCode);
            response.close();
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
