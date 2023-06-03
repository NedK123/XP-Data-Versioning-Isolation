package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MongoOrderValidationDefinitionStorage implements OrderValidationDefinitionStorage {

    private MongoOrderValidationDefinitionRepo repo;
    private MongoReadOrderValidationDefinitionRepo readRepo;

    @Override
    public OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request) {
        OrderValidationDefinitionEntity entity = repo.save(toPersistenceModel(request));
        return toDomainModel(entity);
    }

    @Override
    public OrderValidationDefinition fetch(String id) throws OrderValidationDefinitionNotFoundException {
        return readRepo.findById(id).map(this::toDomainModel).orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    private static OrderValidationDefinitionEntity toPersistenceModel(CreateOrderValidationDefinitionRequest request) {
        return OrderValidationDefinitionEntity.builder().id(UUID.randomUUID().toString())
                .checks(map(request)).build();
    }

    private static Set<String> map(CreateOrderValidationDefinitionRequest request) {
        return request.getChecks().stream().map(OrderChecks::toString).collect(Collectors.toSet());
    }

    private OrderValidationDefinition toDomainModel(OrderValidationDefinitionEntity entity) {
        return OrderValidationDefinition.builder().id(entity.getId()).checks(map(entity)).build();
    }

    private static Set<OrderChecks> map(OrderValidationDefinitionEntity entity) {
        return entity.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet());
    }

}
