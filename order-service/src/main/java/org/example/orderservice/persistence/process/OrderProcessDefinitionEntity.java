package org.example.orderservice.persistence.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("OrderProcessDefinition")
public class OrderProcessDefinitionEntity implements Serializable {
    @Id
    private String id;
    private String name;
    private List<String> validationDefinitions;
    private String shippingPreparationDefinition;
}
