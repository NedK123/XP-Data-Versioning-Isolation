package org.example.orderservice.persistence.validation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class OrderValidationDefinitionEntity implements Serializable {
    @Id
    private String id;
    private Set<String> checks;
}
