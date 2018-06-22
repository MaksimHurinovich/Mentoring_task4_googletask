package by.gurinovich.googletask.util;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class JsonRequestsManager {

    private static JsonArray readJson() {
        try {
            return new JsonParser()
                    .parse(new FileReader("src/test/resources/requests.json"))
                    .getAsJsonArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JsonArray();
        }
    }


    public static ArrayList<String> getRequests(String searcher) {
        ArrayList<String> requests = new ArrayList<>();
        JsonArray array = readJson();
        for (Object o : array) {
            JsonObject object = (JsonObject) o;
            String searcherUpperCase = searcher.toUpperCase();
            String jsonSearcher = object.get("searcher").getAsString();
            if (searcherUpperCase.equals(jsonSearcher)) {
                JsonArray temp = object.getAsJsonArray("requests");
                for (JsonElement r : temp) {
                    requests.add(r.getAsString());
                }
                break;
            }
        }
        return requests;
    }

    public static ArrayList<String> getWrongRequests(String searcher) {
        ArrayList<String> requests = new ArrayList<>();
        JsonArray array = readJson();
        for (Object o : array) {
            JsonObject object = (JsonObject) o;
            String searcherUpperCase = searcher.toUpperCase();
            String jsonSearcher = object.get("searcher").getAsString();
            if (searcherUpperCase.equals(jsonSearcher)) {
                JsonArray temp = object.getAsJsonArray("wrongRequests");
                for (JsonElement r : temp) {
                    requests.add(r.getAsString());
                }
                break;
            }
        }
        return requests;
    }
}
