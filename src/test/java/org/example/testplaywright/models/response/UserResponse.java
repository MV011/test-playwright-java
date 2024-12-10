package org.example.testplaywright.models.response;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String username;
    private Integer id;
    @JsonProperty("is_active")
    private Boolean isActive;
}
