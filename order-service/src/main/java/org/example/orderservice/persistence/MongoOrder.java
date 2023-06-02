package org.example.orderservice.persistence;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.CreateOrderProcessDefinitionRequest;
import org.example.orderservice.core.OrderProcessDefinitionException;
import org.example.orderservice.core.OrderProcessDefinitionStorage;
import org.example.orderservice.process.OrderProcessDefinition;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class MongoOrder implements OrderProcessDefinitionStorage {

    private OrderProcessDefinitionRepo repo;

    @Override
    public OrderProcessDefinition create(CreateOrderProcessDefinitionRequest request) {
        OrderProcessDefinitionEntity entity = repo.save(toPersistenceModel(request));
        return toDomainModel(entity);
    }

    @Override
    public OrderProcessDefinition fetch(String id) throws OrderProcessDefinitionException {
        return repo.findById(id).map(this::toDomainModel).orElseThrow(OrderProcessDefinitionException::new);
    }

    private OrderProcessDefinition toDomainModel(OrderProcessDefinitionEntity entity) {
        return OrderProcessDefinition.builder().id(entity.getId())
                .validationDefinitions(entity.getValidationDefinitions())
                .shippingPreparationDefinition(entity.getShippingPreparationDefinition()).build();
    }

    private OrderProcessDefinitionEntity toPersistenceModel(CreateOrderProcessDefinitionRequest request) {
        return OrderProcessDefinitionEntity.builder().id(UUID.randomUUID().toString())
                .validationDefinitions(request.getValidationDefinitions())
                .shippingPreparationDefinition(request.getShippingPreparationDefinition()).build();
    }

}
