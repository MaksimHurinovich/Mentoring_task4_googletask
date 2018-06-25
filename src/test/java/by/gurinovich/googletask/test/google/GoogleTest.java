package by.gurinovich.googletask.test.google;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.google.GoogleMainPage;
import by.gurinovich.googletask.pageobject.google.GoogleSearchResultsPage;
import by.gurinovich.googletask.util.JsonRequestsManager;
import by.gurinovich.googletask.util.TextUtil;
import com.google.inject.Inject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Guice(modules = GoogleGuiceModule.class)
public class GoogleTest {

    @Inject
    private GoogleMainPage mainPage;
    @Inject
    private GoogleSearchResultsPage resultsPage;

    @BeforeClass
    public void init() {
        mainPage.toRussian();
    }

    @Test(dataProvider = "badDP")
    public void autocompleteNotAppearsTest(String request) {
        mainPage.typeWrongRequest(request);
        Assert.assertEquals(mainPage.getVariationsCount(), 0, "ERROR: autocomplete on request '" + request + "' is wrong");
        mainPage.clearField();
    }

    @Test
    public void mainPageTitleCheck() {
        Assert.assertEquals(mainPage.getDriver().getTitle(), "Google", "ERROR: Title is wrong.");
    }

    @Test(dataProvider = "googleDP")
    public void linkContainsRequestTest(String request) {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        List<String> wordsInRequest = TextUtil.textToWords(request);
        for (int i = 0; i < 5; i++) {
            List<String> linkTextWords = TextUtil.textToWords(resultsPage.getLinkText(resultsPage.getLink(i)));
            softAssert.assertFalse(Collections.disjoint(wordsInRequest, linkTextWords), "ERROR in link#" + i);
        }
        resultsPage.navigateBack();
        softAssert.assertAll();
    }

    @Test(dataProvider = "googleDP")
    public void checkStatusCodeTest(String request) throws IOException {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        CloseableHttpClient client = HttpClients.createDefault();
        for (int i = 0; i < 3; i++) {
            String url = resultsPage.getLink(i).getAttribute("href");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            softAssert.assertEquals(statusCode, 200, "ERROR: in link#" + i + " status code is " + statusCode);
            response.close();
        }
        resultsPage.navigateBack();
        softAssert.assertAll();
    }

    @Test(dataProvider = "googleDP")
    public void checkResponseContentTest(String request) throws IOException {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        CloseableHttpClient client = HttpClients.createDefault();
        for (int i = 0; i < 3; i++) {
            String url = resultsPage.getLink(i).getAttribute("href");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            long contentLength = response.getEntity().getContentLength();
            softAssert.assertNotEquals(contentLength, 0, "ERROR: link#" + i + " has no content");
            response.close();
        }
        resultsPage.navigateBack();
        softAssert.assertAll();
    }

    @DataProvider(name = "googleDP")
    public Object[] googleDataProvider() {
        ArrayList<String> requests = JsonRequestsManager.getRequests("google");
        return requests.toArray();
    }

    @DataProvider(name = "badDP")
    public Object[] badRequestsProvider() {
        ArrayList<String> requests = JsonRequestsManager.getWrongRequests("google");
        return requests.toArray();
    }
}

