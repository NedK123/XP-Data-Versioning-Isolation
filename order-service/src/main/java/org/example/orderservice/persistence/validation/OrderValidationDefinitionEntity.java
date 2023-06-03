package org.example.orderservice.persistence.validation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "order_validation_definition")
public class OrderValidationDefinitionEntity implements Serializable {
    @Id
    private String id;
    private String name;
    private Set<String> checks;
}
