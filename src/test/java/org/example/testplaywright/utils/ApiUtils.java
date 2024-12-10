package org.example.testplaywright.utils;

import com.microsoft.playwright.APIResponse;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ApiUtils {

    public static <T> T convertResponseBody(APIResponse response, Class<T> modelClass) {
        return deserializeJson(new String(response.body()), modelClass);
    }

    public static <T> T deserializeJson(String json, Class<T> modelClass) {
        try {
        return new ObjectMapper().readValue(json, modelClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse response body to " + modelClass.getSimpleName(), e);
        }
    }

    public static <T> List<T> deserializeJsonArray(String json, Class<T> modelClass) {
        try {
            return new ObjectMapper().readValue(json, new ObjectMapper().getTypeFactory().constructCollectionType(List.class, modelClass));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse response body to " + modelClass.getSimpleName(), e);
        }
    }
}
