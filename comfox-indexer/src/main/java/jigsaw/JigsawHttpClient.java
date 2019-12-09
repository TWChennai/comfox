package jigsaw;

import config.ConfigManager;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.valueOf;
import static jigsaw.json.util.JsonUtils.mergeJsonArrays;

public class JigsawHttpClient {
    private static final String SCHEME = "https";
    private static final String API_BASE_URL = "jigsaw.thoughtworks.com/api";
    private static final String PEOPLE_PATH = "/people";
    private static final String JIGSAW_AUTH_TOKEN = ConfigManager.getJigsawProperty("jigsaw_auth_token");
    private static final String ASSIGNMENTS_PATH = "/assignments";

    private CloseableHttpResponse sendGetRequest(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(httpGet);
    }

    private HttpGet getHttpGetRequest(URI uri) {
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", JIGSAW_AUTH_TOKEN);
        return httpGet;
    }

    private int getTotalPageCount(URI uri) throws IOException {
        CloseableHttpResponse httpResponse = sendGetRequest(getHttpGetRequest(uri));
        try {
            String totalPages = httpResponse.getFirstHeader("X-Total-Pages").getValue().trim();
            return Integer.parseInt(totalPages);
        } finally {
            httpResponse.close();
        }
    }

    private String getHttpResponse(URI uri) throws IOException {
        CloseableHttpResponse httpResponse = sendGetRequest(getHttpGetRequest(uri));
        try {
            return EntityUtils.toString(httpResponse.getEntity());
        } finally {
            httpResponse.close();
        }
    }

    private URIBuilder getUriBuilderForThePath(String path) {
        return new URIBuilder().setScheme(SCHEME).setHost(API_BASE_URL).setPath(path);
    }

    private String getPaginatedResponseMerged(URIBuilder uriBuilder) throws URISyntaxException, IOException {
        int pageCount = getTotalPageCount(uriBuilder.build());
        String jsonResponse = "[]";
        for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
            URI uri = uriBuilder.setParameter("page", valueOf(pageNumber)).build();
            jsonResponse = mergeJsonArrays(jsonResponse, getHttpResponse(uri));
        }
        return jsonResponse;
    }

    public String getPeopleWithWorkingOffice(String workingOffice) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = getUriBuilderForThePath(PEOPLE_PATH).setParameter("working_office", workingOffice);
        return getPaginatedResponseMerged(uriBuilder);
    }

    public String getAssignments() throws IOException, URISyntaxException {
        URIBuilder uriBuilder = getUriBuilderForThePath(ASSIGNMENTS_PATH).setParameter("current_only", "true").setParameter("project_type", "ExternalProject")
                .setParameter("project_status", "Active");
        return getPaginatedResponseMerged(uriBuilder);
    }
}
