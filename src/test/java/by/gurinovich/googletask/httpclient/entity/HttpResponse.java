package by.gurinovich.googletask.httpclient.entity;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    public Pair<Integer, String> getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(Pair<Integer, String> statusLine) {
        this.statusLine = statusLine;
    }

    private Pair<Integer, String> statusLine;
    private Map<String, String> headers = new HashMap<>();
    private String responseContent;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    public void addHeader(String name, String value){
        headers.put(name, value);
    }

    public int getStatusCode(){
        return statusLine.getKey();
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "\nstatusCode=" + statusLine.getKey() +
                ",\nmessage=" + statusLine.getValue() +
                ",\nheaders=" + headers +
                ",\nresponseContent='" + responseContent + '\'' +
                '}';
    }
}
