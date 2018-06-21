package by.gurinovich.googletask;

import by.gurinovich.googletask.core.Driver;
import by.gurinovich.googletask.page_objects.GoogleMainPage;
import by.gurinovich.googletask.util.JsonConverter;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TestRun {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> requests = JsonConverter.convertJsonSearcher("google");
        System.out.println(requests.toString());
        GoogleMainPage page = new GoogleMainPage(Driver.createDriver());
        page.toRussian();
        for (String request : requests) {
            page.typeRequest(request);
            System.out.println(page.getVariationsCount());
            page.clearField();
        }
    }
}
