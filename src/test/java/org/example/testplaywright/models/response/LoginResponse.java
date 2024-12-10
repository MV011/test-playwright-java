package org.example.testplaywright.models.response;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty( "token_type")
    private String tokenType;
}
