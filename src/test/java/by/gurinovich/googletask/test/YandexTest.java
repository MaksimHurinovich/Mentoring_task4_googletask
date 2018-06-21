package by.gurinovich.googletask.test;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.pageobject.yandex.YandexMainPage;
import by.gurinovich.googletask.pageobject.yandex.YandexSearchResultsPage;
import by.gurinovich.googletask.util.JsonConverter;
import by.gurinovich.googletask.util.TextUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YandexTest {

    private YandexMainPage mainPage;
    private YandexSearchResultsPage resultsPage;

    @BeforeClass
    public void init() {
        mainPage = new YandexMainPage(Driver.createDriver());
        resultsPage = new YandexSearchResultsPage(mainPage.getDriver());
    }

    @AfterClass
    public void destroy() {
        mainPage.getDriver().quit();
    }

    @Test
    public void yandexTitleCheck() {
        Assert.assertEquals(mainPage.getDriver().getTitle(), "Яндекс", "ERROR: title is not correct.");
    }

    @Test(dataProvider = "yandexDP")
    public void linkContainsRequestTest(String request) {
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

    @Test(dataProvider = "wrongDP")
    public void badRequestsTest(String request) {
        mainPage.doSearch(request);
        int resultsSize = resultsPage.searchResultsSize();
        mainPage.navigateToMain();
        Assert.assertEquals(resultsSize, 0, "ERROR: in bad request " + request);
    }

    @DataProvider(name = "yandexDP")
    public Object[] yandexDataProvider() {
        ArrayList<String> requests = JsonConverter.getRequests("yandex");
        return requests.toArray();
    }

    @DataProvider(name = "wrongDP")
    public Object[] yandexWrongRequestsProvider() {
        ArrayList<String> wrongRequests = JsonConverter.getWrongRequests("yandex");
        return wrongRequests.toArray();
    }
}
