package org.example.orderservice.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProcessDefinition {
    private String id;
    private List<String> validationDefinitions;
    private String shippingPreparationDefinition;
}
