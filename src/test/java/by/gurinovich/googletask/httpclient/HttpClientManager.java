package by.gurinovich.googletask.httpclient;

import by.gurinovich.googletask.pageobject.ResultPage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpClientManager {

    public static boolean checkOKStatusCode(ResultPage resultAction) {
        boolean flag = true;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < resultAction.searchResultsSize() - 5; i++) {
            String currentUrl = resultAction.getLink(i).getAttribute("href");
            HttpGet get = new HttpGet(currentUrl);
            CloseableHttpResponse response;
            try {
                response = httpClient.execute(get);
                if (response.getStatusLine().getStatusCode() != 200) {
                    flag = false;
                    System.out.println("Link#" + i + " has wrong status code.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public static boolean checkResponseContentNotEmpty(ResultPage resultAction) {
        boolean flag = true;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < resultAction.searchResultsSize() - 5; i++) {
            String currentUrl = resultAction.getLink(i).getAttribute("href");
            HttpGet get = new HttpGet(currentUrl);
            CloseableHttpResponse response;
            try {
                response = httpClient.execute(get);
                if (response.getEntity().getContentLength() == 0) {
                    flag = false;
                    System.out.println("Link#" + i + " has empty content.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
