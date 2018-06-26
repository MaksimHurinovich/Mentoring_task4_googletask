package by.gurinovich.googletask.test.yandex;

import by.gurinovich.googletask.guice.YandexGuiceModule;
import by.gurinovich.googletask.httpclient.HttpClient;
import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import by.gurinovich.googletask.pageobject.yandex.YandexMainPage;
import by.gurinovich.googletask.pageobject.yandex.YandexSearchResultsPage;
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

@Guice(modules = YandexGuiceModule.class)
public class YandexTest {

    @Inject
    private YandexMainPage mainPage;
    @Inject
    private YandexSearchResultsPage resultsPage;

    @Test
    public void yandexTitleCheck() {
        mainPage.navigateToMain();
        Assert.assertEquals(mainPage.getDriver().getTitle(), "Яндекс", "ERROR: title is not correct.");
    }

    @Test(dataProvider = "yandexDP")
    public void linkContainsRequestTest(String request) {
        SoftAssert softAssert = new SoftAssert();
        mainPage.doSearch(request);
        List<String> wordsInRequest = TextUtil.textToWords(request);
        for (int i = 0; i < 5; i++) {
            List<String> linkTextWords = TextUtil.textToWords(resultsPage.getLinkText(i));
            softAssert.assertFalse(Collections.disjoint(wordsInRequest, linkTextWords), "ERROR in link#" + i + ": " + wordsInRequest + " " + linkTextWords);
        }
        mainPage.navigateToMain();
        softAssert.assertAll();
    }

    @Test(dataProvider = "wrongDP")
    public void badRequestsTest(String request) {
        mainPage.doSearch(request);
        int resultsSize = resultsPage.searchResultsSize();
        mainPage.navigateToMain();
        Assert.assertEquals(resultsSize, 0, "ERROR: in bad request " + request);
    }

    @Test(dataProvider = "yandexDP")
    public void checkStatusCodesTest(String request) {
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

    @DataProvider(name = "yandexDP")
    public Object[] yandexDataProvider() {
        ArrayList<String> requests = JsonRequestsManager.getRequests("yandex");
        return requests.toArray();
    }

    @DataProvider(name = "wrongDP")
    public Object[] yandexWrongRequestsProvider() {
        ArrayList<String> wrongRequests = JsonRequestsManager.getWrongRequests("yandex");
        return wrongRequests.toArray();
    }
}
