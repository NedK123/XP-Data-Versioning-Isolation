package org.example.orderservice.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProcessDefinitionEntity implements Serializable {
    @Id
    private String id;
    private List<String> validationDefinitions;
    private String shippingPreparationDefinition;
}
