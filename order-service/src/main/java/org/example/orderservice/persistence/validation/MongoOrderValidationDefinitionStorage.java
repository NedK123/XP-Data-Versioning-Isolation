package org.example.orderservice.persistence.validation;

import org.example.orderservice.core.validation.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MongoOrderValidationDefinitionStorage implements OrderValidationDefinitionStorage {

    private MongoOrderValidationDefinitionRepo repo;
    private MongoReadOrderValidationDefinitionRepo readRepo;

    @Override
    public OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request) {
        OrderValidationDefinitionEntity entity = repo.save(OrderValidationDefinitionEntity.builder().build());
        return toDomainModel(entity);
    }

    @Override
    public OrderValidationDefinition fetch(String id) throws OrderValidationDefinitionNotFoundException {
        return readRepo.findById(id).map(this::toDomainModel).orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    private OrderValidationDefinition toDomainModel(OrderValidationDefinitionEntity entity) {
        return OrderValidationDefinition.builder().id(entity.getId()).checks(map(entity)).build();
    }

    private static Set<OrderChecks> map(OrderValidationDefinitionEntity entity) {
        return entity.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet());
    }

}
