package org.example.orderservice.api.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderValidationDefinitionApiRequest implements Serializable {
    private String name;
    private Set<String> checks;
}
