package org.example.testplaywright.models;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;

public interface BaseJsonModel {

    default String toJsonString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
