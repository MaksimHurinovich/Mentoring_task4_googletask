package by.gurinovich.googletask.httpclient;

import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import javafx.util.Pair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

public class HttpClient {

    private CloseableHttpClient client = HttpClients.createDefault();

    public HttpResponse get(String url) {
        return execute(new HttpGet(url), url);
    }

    public HttpResponse post(String url) {
        return execute(new HttpPost(), url);
    }

    public HttpResponse put(String url) {
        return execute(new HttpPut(), url);
    }

    public HttpResponse delete(String url) {
        return execute(new HttpDelete(), url);
    }

    private HttpResponse execute(HttpRequestBase base, String url) {
        base.setURI(URI.create(url));
        HttpResponse response = new HttpResponse();
        try {
            CloseableHttpResponse apacheResponse = client.execute(base);
            response = getResponseInfo(apacheResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private HttpResponse getResponseInfo(CloseableHttpResponse apacheResponse) throws IOException {
        HttpResponse response = new HttpResponse();
        Integer statusCode = apacheResponse.getStatusLine().getStatusCode();
        String message = apacheResponse.getStatusLine().getReasonPhrase();
        response.setStatusLine(new Pair<>(statusCode, message));
        HttpEntity contentEntity = apacheResponse.getEntity();
        Header[] headers = apacheResponse.getAllHeaders();
        String content = EntityUtils.toString(contentEntity);
        Integer contentLength = content.length();
        response.setHeaders(new HashMap<>() {{
            for (Header header : headers) {
                put(header.getName(), header.getValue());
            }
            put("Content-Length", Integer.toString(contentLength));
        }});
        response.setResponseContent(content);
        return response;
    }
}
