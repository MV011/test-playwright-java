package org.example.testplaywright.models.response;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CurrentUserResponse {

    private String email;
    private String username;
    private Integer id;
    @JsonProperty("is_active")
    private Boolean isActive;
}
