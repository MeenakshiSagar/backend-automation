package com.saralapp.tests.person;

import com.saralapp.tests.BaseTest;
import io.restassured.response.Response;
import models.person.PersonRequest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.PersonService;
import services.UploadService;
import utils.JsonUtils;
import org.testng.Reporter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ImageUploadFieldTests extends BaseTest {

    @DataProvider(name = "generateUrlCases")
    public Object[][] generateUrlCases() {
        return new Object[][]{
                {"image.jpg", 200, "Valid jpg"},
                {"image.jpeg", 200, "Valid jpeg"},
                {"image.png", 200, "Valid png"},
                {"image.webp", 200, "Valid webp"},
                {"document.xls", 400, "Invalid extension xls"},
                {"doc.pdf", 400, "Invalid extension pdf"},
                {null, 400, "Missing file name"},
                {"my image.jpeg", 200, "File name with spaces"},
                {generateLongName(260) + ".jpeg", 200, "Very long file name (>255)"},
                {"image@$.jpeg", 200, "Special chars in name"}
        };
    }

    @Test(dataProvider = "generateUrlCases")
    public void testGenerateUploadUrl(String fileName, int expectedStatus, String description) {
        Response response = UploadService.generateUploadUrl(fileName == null ? "" : fileName);
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Case: " + description + " | Response: " + response.getBody().asString());

        if (expectedStatus == 200) {
            // Expect a JSON with 'url' key
            Assert.assertTrue(response.getBody().asString().contains("url"), "Signed URL key missing | Case: " + description);
        } else {
            // For 400 we expect an error message
            Assert.assertFalse(response.getBody().asString().isEmpty(), "Expected error message for case: " + description);
        }
    }

    @Test
    public void testUploadFlow_validJpeg() {
        String fileName = "voter.jpeg";
        Response gen = UploadService.generateUploadUrl(fileName);
        Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL");
        String signedUrl = gen.jsonPath().getString("url");
        Assert.assertNotNull(signedUrl, "Signed URL missing in response");

        // small binary payload to simulate image
        byte[] payload = "binaryjpegdata".getBytes(StandardCharsets.UTF_8);
        Response putResp = UploadService.putToSignedUrl(signedUrl, payload, "image/jpeg");
        System.out.println("PUT response status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString());
        Reporter.log("PUT response status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString(), true);
        Assert.assertEquals(putResp.getStatusCode(), 200, "PUT to signed URL failed");

        // set URL in person payload and create person
        PersonRequest person = new PersonRequest();
        person.setName("TestName");
        person.setPhoneNumber("9000000000");
        person.setAssemblyConstituency(365);
        person.setVoterUrl(signedUrl);

        String payloadJson = JsonUtils.toJson(person);
        System.out.println("Person payload:\n" + payloadJson);
        Reporter.log("Person payload:\n" + payloadJson, true);

        Response createResp = PersonService.createPerson(person);
        Assert.assertTrue(createResp.getStatusCode() == 200 || createResp.getStatusCode() == 201, "Person creation failed after upload");
    }

    @Test
    public void testUploadFlow_forAllImageFields() {
        String[] fileNames = {"ration.jpg", "voter.jpg", "aadhaar.jpg"};
        String[] fields = {"ration_card_url", "voter_url", "aadhaar_url"};
        String[] contentTypes = {"image/jpeg", "image/jpeg", "image/jpeg"};

        for (int i = 0; i < fileNames.length; i++) {
            Response gen = UploadService.generateUploadUrl(fileNames[i]);
            Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL for " + fileNames[i]);
            String signedUrl = gen.jsonPath().getString("url");
            Assert.assertNotNull(signedUrl, "Signed URL missing in response for " + fileNames[i]);

            byte[] payload = ("binarydata" + i).getBytes(StandardCharsets.UTF_8);
            Response putResp = UploadService.putToSignedUrl(signedUrl, payload, contentTypes[i]);
            System.out.println("PUT response for " + fileNames[i] + " -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString());
            Reporter.log("PUT response for " + fileNames[i] + " -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString(), true);
            Assert.assertEquals(putResp.getStatusCode(), 200, "PUT to signed URL failed for " + fileNames[i]);

            // create person with that image URL in corresponding field
            PersonRequest person = new PersonRequest();
            person.setName("ImgTest");
            person.setPhoneNumber("9000000000");
            person.setAssemblyConstituency(365);
            switch (i) {
                case 0 -> person.setRationCardUrl(signedUrl);
                case 1 -> person.setVoterUrl(signedUrl);
                case 2 -> person.setAadhaarUrl(signedUrl);
            }

            String payloadJson = JsonUtils.toJson(person);
            System.out.println("Person payload:\n" + payloadJson);
            Reporter.log("Person payload:\n" + payloadJson, true);

            Response createResp = PersonService.createPerson(person);
            Assert.assertTrue(createResp.getStatusCode() == 200 || createResp.getStatusCode() == 201,
                    "Person creation failed after upload for field: " + fields[i]);
        }
    }

    @Test
    public void testUploadFlow_expiredUrl() throws InterruptedException {
        String fileName = "expired.png";
        Response gen = UploadService.generateUploadUrl(fileName);
        Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL");
        String signedUrl = gen.jsonPath().getString("url");
        Assert.assertNotNull(signedUrl, "Signed URL missing in response");

        // Sleep briefly to simulate expiry (adjust per environment; using 70s for 1-minute expiry)
        TimeUnit.SECONDS.sleep(70);

        byte[] payload = "binarypngdata".getBytes(StandardCharsets.UTF_8);
        Response putResp = UploadService.putToSignedUrl(signedUrl, payload, "image/png");
        System.out.println("Expired PUT response -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString());
        Reporter.log("Expired PUT response -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString(), true);
        Assert.assertTrue(putResp.getStatusCode() == 403 || putResp.getStatusCode() == 401, "Expected forbidden/unauthorized for expired URL");
    }

    @Test
    public void testUploadFlow_wrongContentType() {
        String fileName = "wrongtype.jpeg";
        Response gen = UploadService.generateUploadUrl(fileName);
        Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL");
        String signedUrl = gen.jsonPath().getString("url");
        Assert.assertNotNull(signedUrl, "Signed URL missing in response");

        byte[] payload = "binaryjpegdata".getBytes(StandardCharsets.UTF_8);
        // Use wrong content type to simulate mismatch
        Response putResp = UploadService.putToSignedUrl(signedUrl, payload, "text/plain");
        System.out.println("Wrong Content-Type PUT response -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString());
        Reporter.log("Wrong Content-Type PUT response -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString(), true);
        Assert.assertTrue(putResp.getStatusCode() == 403 || putResp.getStatusCode() == 400, "Expected forbidden or bad request for wrong content type");
    }

    @Test
    public void testUploadFlow_missingContentType() {
        String fileName = "notype.jpeg";
        Response gen = UploadService.generateUploadUrl(fileName);
        Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL");
        String signedUrl = gen.jsonPath().getString("url");
        Assert.assertNotNull(signedUrl, "Signed URL missing in response");

        byte[] payload = "binaryjpegdata".getBytes(StandardCharsets.UTF_8);
        Response putResp = UploadService.putToSignedUrlNoContentType(signedUrl, payload);
        System.out.println("Missing Content-Type PUT response -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString());
        Reporter.log("Missing Content-Type PUT response -> status: " + putResp.getStatusCode() + ", body: " + putResp.getBody().asString(), true);
        Assert.assertTrue(putResp.getStatusCode() == 403 || putResp.getStatusCode() == 400, "Expected forbidden or bad request when Content-Type missing");
    }

    @Test
    public void testUploadFlow_wrongHttpMethod() {
        String fileName = "wrongmethod.jpeg";
        Response gen = UploadService.generateUploadUrl(fileName);
        Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL");
        String signedUrl = gen.jsonPath().getString("url");
        Assert.assertNotNull(signedUrl, "Signed URL missing in response");

        byte[] payload = "binaryjpegdata".getBytes(StandardCharsets.UTF_8);
        Response resp = UploadService.requestToSignedUrlWithMethod(signedUrl, payload, "image/jpeg", "POST");
        // Some servers return 405, some return 403; accept both
        System.out.println("Wrong HTTP method response -> status: " + resp.getStatusCode() + ", body: " + resp.getBody().asString());
        Reporter.log("Wrong HTTP method response -> status: " + resp.getStatusCode() + ", body: " + resp.getBody().asString(), true);
        Assert.assertTrue(resp.getStatusCode() == 405 || resp.getStatusCode() == 403, "Expected method not allowed or forbidden for wrong HTTP method");
    }

    @Test
    public void testUploadFlow_tamperedUrl() {
        String fileName = "tamper.jpeg";
        Response gen = UploadService.generateUploadUrl(fileName);
        Assert.assertEquals(gen.getStatusCode(), 200, "Failed to get signed URL");
        String signedUrl = gen.jsonPath().getString("url");
        Assert.assertNotNull(signedUrl, "Signed URL missing in response");

        String tampered = UploadService.tamperSignedUrl(signedUrl);
        byte[] payload = "binaryjpegdata".getBytes(StandardCharsets.UTF_8);
        Response resp = UploadService.putToSignedUrl(tampered, payload, "image/jpeg");
        System.out.println("Tampered URL PUT response -> status: " + resp.getStatusCode() + ", body: " + resp.getBody().asString());
        Reporter.log("Tampered URL PUT response -> status: " + resp.getStatusCode() + ", body: " + resp.getBody().asString(), true);
        Assert.assertTrue(resp.getStatusCode() == 403 || resp.getStatusCode() == 400, "Expected forbidden or bad request for tampered signed URL");
    }

    private static String generateLongName(int length) {
        return "a".repeat(length);
    }
}
