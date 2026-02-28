package services;

import config.Endpoints;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import utils.AuthManager;
import utils.RequestManager;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UploadService {

    public static Response generateUploadUrl(String fileName) {
        Map<String, Object> params = new HashMap<>();
        params.put("file_name", fileName);

        return RequestManager.sendRequest()
                .header("Authorization", "Bearer " + AuthManager.getValidToken())
                .queryParams(params)
                .get(Endpoints.GENERATE_UPLOAD_URL);
    }

    public static Response putToSignedUrl(String signedUrl, byte[] fileBytes, String contentType) {
        return requestToSignedUrlWithMethod(signedUrl, fileBytes, contentType, "PUT");
    }

    public static Response putToSignedUrlNoContentType(String signedUrl, byte[] fileBytes) {
        return requestToSignedUrlWithMethod(signedUrl, fileBytes, null, "PUT");
    }

    public static Response requestToSignedUrlWithMethod(String signedUrl, byte[] fileBytes, String contentType, String method) {
        byte[] bytes = fileBytes == null ? new byte[0] : fileBytes;

        // Build a CloseableHttpClient and supply it to RestAssured so underlying client sends Content-Length
        CloseableHttpClient client = HttpClients.custom().useSystemProperties().build();
        RestAssuredConfig config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                .httpClientFactory(() -> client));

        // Debug logging
        String urlNoQuery = signedUrl;
        int q = signedUrl.indexOf('?');
        if (q > 0) urlNoQuery = signedUrl.substring(0, q);
        System.out.println("[UploadService] Preparing request -> method=" + method + ", url=" + urlNoQuery + ", Content-Type=" + contentType + ", Content-Length=" + bytes.length);

        io.restassured.specification.RequestSpecification req = RestAssured.given().config(config).relaxedHTTPSValidation();
        if (contentType != null) req.contentType(contentType);
        req.body(bytes);

        // Execute request
        Response resp = req.request(method, signedUrl);

        // Log response for debugging
        System.out.println("[UploadService] PUT response status=" + resp.getStatusCode() + ", body=" + resp.getBody().asString());

        return resp;
    }

    public static String tamperSignedUrl(String signedUrl) {
        if (signedUrl.contains("?")) {
            return signedUrl + "&tamper=1";
        } else {
            return signedUrl + "?tamper=1";
        }
    }
}
