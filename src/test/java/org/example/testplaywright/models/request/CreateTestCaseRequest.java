package org.example.testplaywright.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.testplaywright.models.BaseJsonModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestCaseRequest implements BaseJsonModel {

    private String title;
    private String description;
    private String status;
}
