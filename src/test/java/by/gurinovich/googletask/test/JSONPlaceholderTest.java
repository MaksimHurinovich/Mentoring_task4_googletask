package by.gurinovich.googletask.test;

import by.gurinovich.googletask.guice.JSONPlaceholderModule;
import by.gurinovich.googletask.httpclient.HttpClient;
import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;


@Guice(modules = JSONPlaceholderModule.class)
public class JSONPlaceholderTest {

    private static final String URL = "https://jsonplaceholder.typicode.com/users";
    @Inject
    private HttpClient client;

    @Test
    public void statusCodeCheck() {
        HttpResponse response = client.get(URL);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not OK");
    }

    @Test
    public void contentTypeCheck() {
        HttpResponse response = client.get(URL);
        Assert.assertEquals(response.getHeaders().get("Content-Type"), "application/json; charset=utf-8", "ContentType is wrong");
    }

    @Test
    public void contentValidationCheck(){
        HttpResponse response = client.get(URL);
        String content = response.getResponseContent();
        JsonArray array = new JsonParser().parse(content).getAsJsonArray();
        Assert.assertEquals(array.size(), 10, "JSON size is not 10.");
    }

}
