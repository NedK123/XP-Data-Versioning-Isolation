package org.example.orderservice.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderProcessDefinitionApiRequest implements Serializable {
    private String name;
    private List<String> validationDefinitions;
    private String shippingPreparationDefinition;
}
