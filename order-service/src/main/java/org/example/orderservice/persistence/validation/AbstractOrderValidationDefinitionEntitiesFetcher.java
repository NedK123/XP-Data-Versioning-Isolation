package org.example.orderservice.persistence.validation;

import org.example.orderservice.core.validation.OrderChecks;
import org.example.orderservice.core.validation.OrderValidationDefinition;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractOrderValidationDefinitionEntitiesFetcher implements OrderValidationDefinitionEntitiesFetcher {

    protected OrderValidationDefinition toDomainModel(OrderValidationDefinitionEntity entity) {
        return OrderValidationDefinition.builder().id(entity.getId()).name(entity.getName()).checks(map(entity)).build();
    }

    private static Set<OrderChecks> map(OrderValidationDefinitionEntity entity) {
        return entity.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet());
    }

}
