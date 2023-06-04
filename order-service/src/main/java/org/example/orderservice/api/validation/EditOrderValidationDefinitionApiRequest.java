package org.example.orderservice.api.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditOrderValidationDefinitionApiRequest {
    private String name;
    private Set<String> checks;
}
