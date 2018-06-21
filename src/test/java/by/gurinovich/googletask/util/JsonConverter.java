package by.gurinovich.googletask.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class JsonConverter {

    public static ArrayList<String> convertJsonSearcher(String searcher) throws FileNotFoundException {
        ArrayList<String> requests = new ArrayList<>();
        JsonArray array = new JsonParser()
                .parse(new FileReader("src/test/resources/requests.json"))
                .getAsJsonArray();
        for(Object o: array){
            JsonObject object = (JsonObject) o;
            String searcherUpperCase = searcher.toUpperCase();
            String jsonSearcher = object.get("searcher").getAsString();
            if(searcherUpperCase.equals(jsonSearcher)){
                JsonArray temp = object.getAsJsonArray("requests");
                for(JsonElement r: temp){
                    requests.add(r.getAsString());
                }
            }
        }
        return requests;
    }
}
