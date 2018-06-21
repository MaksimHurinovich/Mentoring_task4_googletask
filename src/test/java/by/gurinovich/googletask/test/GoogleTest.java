package by.gurinovich.googletask.test;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.GoogleMainPage;
import by.gurinovich.googletask.pageobject.GoogleSearchResultsPage;
import by.gurinovich.googletask.util.JsonConverter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
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

    @Test(dataProvider = "googleDP")
    public void autocompleteAppearanceTest(String request) {
        mainPage.typeCorrectRequest(request);
        int variations = mainPage.getVariationsCount();
        mainPage.clearField();
        Assert.assertNotEquals(variations, 0, "ERROR: autocomplete on request '" + request + "' is wrong");
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

    @Test(dataProvider = "googleDP")
    public void checkStatusCodeTest(String request) throws IOException {
        mainPage.typeRequest(request);
        mainPage.doSearch();
        CloseableHttpClient client = HttpClients.createSystem();
        for (int i = 0; i < resultsPage.resultListSize(); i++) {
            String url = resultsPage.getLink(i).getAttribute("href");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            softAssert.assertEquals(statusCode, 200, "ERROR: in link#" + i + " status code is " + statusCode);
            resultsPage.navigateBack();
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "googleDP")
    public void checkResponseContentTest(String request) throws IOException {
        mainPage.typeRequest(request);
        mainPage.doSearch();
        CloseableHttpClient client = HttpClients.createSystem();
        for (int i = 0; i < resultsPage.resultListSize(); i++) {
            String url = resultsPage.getLink(i).getAttribute("href");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);
            long contentLength = response.getEntity().getContentLength();
            softAssert.assertNotEquals(contentLength, 0, "ERROR: link#" + i + " has no content");
            resultsPage.navigateBack();
        }
        softAssert.assertAll();
    }
    @DataProvider(name = "googleDP")
    public Object[] googleDataProvider() {
        ArrayList<String> requests = JsonConverter.getRequests("google");
        return requests.toArray();
    }

    private List<String> textToWords(String text) {
        return Arrays.asList(text.toUpperCase().replaceAll("[{}\\[\\](),.\"!?<>:;]", "").split(" "));
    }
}

