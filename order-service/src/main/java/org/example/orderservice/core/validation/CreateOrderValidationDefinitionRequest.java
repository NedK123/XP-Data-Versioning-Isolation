package org.example.orderservice.core.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderValidationDefinitionRequest {
    private String name;
    private Set<OrderChecks> checks;
}
