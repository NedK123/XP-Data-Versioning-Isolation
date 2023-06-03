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
public class OrderValidationDefinition {
    private String id;
    private String name;
    private Set<OrderChecks> checks;
}
