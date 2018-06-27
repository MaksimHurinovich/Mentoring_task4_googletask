package by.gurinovich.googletask.test.desktop.google;

import by.gurinovich.googletask.guice.GoogleGuiceModule;
import by.gurinovich.googletask.httpclient.HttpClient;
import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import by.gurinovich.googletask.pageobject.google.GoogleMainPage;
import by.gurinovich.googletask.pageobject.google.GoogleSearchResultsPage;
import by.gurinovich.googletask.util.JsonRequestsManager;
import by.gurinovich.googletask.util.TextUtil;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
            List<String> linkTextWords = TextUtil.textToWords(resultsPage.getLinkText(i));
            softAssert.assertFalse(Collections.disjoint(wordsInRequest, linkTextWords), "ERROR in link#" + i);
        }
        resultsPage.navigateBack();
        softAssert.assertAll();
    }

    @Test(dataProvider = "googleDP")
    public void checkStatusCodeTest(String request) {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        HttpClient client = new HttpClient();
        for(int i = 0; i < resultsPage.searchResultsSize() - 3; i++){
            HttpResponse response = client.get(resultsPage.getLinkURL(i));
            softAssert.assertEquals(response.getStatusCode(), 200, "Link#" + i + " has wrong status code,");
        }
        resultsPage.navigateBack();
        softAssert.assertAll();
    }

    @Test(dataProvider = "googleDP")
    public void checkResponseContentTest(String request) {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        HttpClient client = new HttpClient();
        for(int i = 0; i < resultsPage.searchResultsSize() - 3; i++){
            HttpResponse response = client.get(resultsPage.getLinkURL(i));
            softAssert.assertNotEquals(response.getHeaders().get("content-length"), 0, "Link#" + i + " has no content,");
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

