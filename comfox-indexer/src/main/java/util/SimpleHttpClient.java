package util;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class SimpleHttpClient {

    public String getResponse(HttpUriRequest request) throws IOException {
        CloseableHttpResponse httpResponse = sendRequest(request);
        try {
            return EntityUtils.toString(httpResponse.getEntity());
        } finally {
            httpResponse.close();
        }
    }

    public HttpGet httpGetRequest(URI uri, Optional<String> authToken) {
        HttpGet httpGet = new HttpGet(uri);
        if (authToken.isPresent()) {
            httpGet.setHeader("Authorization", authToken.get());
        }
        return httpGet;
    }

    public HttpPost httpPostRequest(URI uri, StringEntity content) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(content);
        return httpPost;
    }

    private CloseableHttpResponse sendRequest(HttpUriRequest request) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(request);
    }
}
