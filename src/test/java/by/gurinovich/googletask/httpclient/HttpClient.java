package by.gurinovich.googletask.httpclient;

import by.gurinovich.googletask.httpclient.entity.HttpResponse;
import javafx.util.Pair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;

public class HttpClient {

    private CloseableHttpClient client = HttpClients.createDefault();

    public HttpResponse get(String url)  {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = new HttpResponse();
        try {
            CloseableHttpResponse apacheResponse = client.execute(httpGet);
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
            for(Header header:headers){
                put(header.getName(), header.getValue());
            }
            put("content-length", contentLength);
        }});
        response.setResponseContent(content);
        return response;
    }
}
