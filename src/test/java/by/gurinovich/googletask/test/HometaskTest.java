package by.gurinovich.googletask.test;

import by.gurinovich.googletask.httpclient.HttpClient;
import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HometaskTest {

    private String url = "https://jsonplaceholder.typicode.com/users";
    private HttpClient client = new HttpClient();

    @Test
    public void statusCodeCheck() {
        HttpResponse response = client.get(url);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not OK");
    }

    @Test
    public void contentTypeCheck() {
        HttpResponse response = client.get(url);
        Assert.assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8", "ContentType is wrong");
    }

}
