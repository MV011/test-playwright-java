package org.example.testplaywright.models.response;

import io.cucumber.core.internal.com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.testplaywright.models.BaseJsonModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCaseResponse implements BaseJsonModel {

    private String title;
    private String description;
    private String status;
    private Integer id;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("owner_id")
    private Integer ownerId;
}
