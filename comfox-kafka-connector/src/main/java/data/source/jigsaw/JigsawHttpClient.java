package data.source.jigsaw;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import config.ConfigManager;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import static data.source.jigsaw.json.util.JsonUtils.mergeJsonArrays;
import static java.lang.String.valueOf;

public class JigsawHttpClient {
    private static final String SCHEME = "https";
    private static final String API_BASE_URL = "jigsaw.thoughtworks.com/api";
    private static final String PEOPLE_PATH = "/people";
    private static final String JIGSAW_AUTH_TOKEN = ConfigManager.getProperty("jigsaw_auth_token");
    private static final String SKILLS_PATH = PEOPLE_PATH + "/id" + "/skills";
    private static final String ASSIGNMENTS_PATH = "/assignments";

    private CloseableHttpResponse sendGetRequest(HttpGet httpGet) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(httpGet);
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

    private String getPaginatedResponseMerged(URIBuilder uriBuilder) throws URISyntaxException, IOException {
        int pageCount = getTotalPageCount(uriBuilder.build());
        String jsonResponse = "[]";
        for(int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
            URI uri = uriBuilder.setParameter("page", valueOf(pageNumber)).build();
            jsonResponse = mergeJsonArrays(jsonResponse, getHttpResponse(uri));
        }
        return jsonResponse;
    }

    public String getPersonsInWorkingOffice(String workingOffice) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = getUriBuilderForThePath(PEOPLE_PATH).setParameter("working_office", workingOffice);
        return getPaginatedResponseMerged(uriBuilder);
    }

    public String getSkillsOfPersonWithEmployeeId(String employeeId) throws URISyntaxException, IOException {
        URI uri = getUriBuilderForThePath(SKILLS_PATH.replaceAll("id", employeeId)).build();
        return getHttpResponse(uri);
    }

    public String getAssignmentsOfPersonWithEmployeeId(String employeeId) throws URISyntaxException, IOException {
        URI uri = getUriBuilderForThePath(ASSIGNMENTS_PATH).setParameter("employee_ids[]", employeeId).build();
        return getHttpResponse(uri);
    }

    private HttpGet getHttpGetRequest(URI uri){
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", JIGSAW_AUTH_TOKEN);
        return httpGet;
    }

    private URIBuilder getUriBuilderForThePath(String path) {
        return new URIBuilder().setScheme(SCHEME).setHost(API_BASE_URL).setPath(path);
    }
}

