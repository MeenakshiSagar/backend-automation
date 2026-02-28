package com.saralapp.helpers;

import config.Endpoints;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.RequestManager;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FormStructureService {

    private static final ObjectMapper M = new ObjectMapper();

    /**
     * Reset the form structure on the server to a minimal state (data=null) so subsequent tests can configure fields.
     * Uses adminRequest() to perform the action with national admin credentials.
     */
    public static Response resetFormStructure() {
        try {
            // Build the minimal reset payload exactly as requested
            String resetPayload = M.writerWithDefaultPrettyPrinter().writeValueAsString(
                    M.readTree("{\n  \"data\": null,\n  \"autofill\": false,\n  \"data_level\": 1,\n  \"country_state_id\": 4,\n  \"data_type\": 1,\n  \"is_app\": false,\n  \"allow_only_camera\": false\n}")
            );

            RequestSpecification adminReq = RequestManager.adminRequest().body(resetPayload);
            Response resp = adminReq.post(Endpoints.SAVE_FORM_STRUCTURE);
            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset form structure: " + e.getMessage(), e);
        }
    }

    /**
     * Marks only the given formControlNames as mandatory in the form structure and uploads it.
     * Uses RequestManager.adminRequest() so admin login is transient and TokenStorage is unaffected.
     */
    public static Response markFieldsMandatoryAndUpload(List<String> formControlNamesToMark) {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("form_structure_template.json")) {
            if (is == null) throw new RuntimeException("form_structure_template.json not found in test resources");
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            JsonNode root = M.readTree(json);
            JsonNode data = root.get("data");
            if (data != null && data.isArray()) {
                for (JsonNode node : data) {
                    JsonNode formControlNameNode = node.get("formControlName");
                    if (formControlNameNode != null) {
                        String fc = formControlNameNode.asText();
                        boolean shouldMark = formControlNamesToMark.contains(fc);
                        ((com.fasterxml.jackson.databind.node.ObjectNode) node).put("mandatoryField", shouldMark);
                    }
                }
            }

            String modified = M.writerWithDefaultPrettyPrinter().writeValueAsString(root);

            RequestSpecification adminReq = RequestManager.adminRequest().body(modified);
            Response resp = adminReq.post(Endpoints.SAVE_FORM_STRUCTURE);
            return resp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
