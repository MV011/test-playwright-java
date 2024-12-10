package org.example.testplaywright.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.testplaywright.models.BaseJsonModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest implements BaseJsonModel {
    private String email;
    private String username;
    private String password;
}