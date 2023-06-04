package org.example.orderservice.persistence.process;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.process.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class MongoOrderProcessDefinitionStorage implements OrderProcessDefinitionStorage {

    private OrderProcessDefinitionRepo repo;

    @Override
    public OrderProcessDefinition create(CreateOrderProcessDefinitionRequest request) {
        OrderProcessDefinitionEntity entity = repo.save(toPersistenceModel(request));
        return toDomainModel(entity);
    }

    @Override
    public OrderProcessDefinition fetch(String id) throws OrderProcessDefinitionNotFoundException {
        return repo.findById(id).map(this::toDomainModel).orElseThrow(OrderProcessDefinitionNotFoundException::new);
    }

    @Override
    public void edit(String id, EditOrderProcessDefinitionRequest request) throws OrderProcessDefinitionNotFoundException {
        OrderProcessDefinitionEntity definitionEntity = repo.findById(id).orElseThrow(OrderProcessDefinitionNotFoundException::new);
        request.ifContainsNameUpdate(definitionEntity::setName);
        request.ifContainsShippingPreparationDefinitionUpdate(definitionEntity::setShippingPreparationDefinition);
        request.ifContainsValidationDefinitionsUpdate(definitionEntity::setValidationDefinitions);
        repo.save(definitionEntity);
    }

    private OrderProcessDefinition toDomainModel(OrderProcessDefinitionEntity entity) {
        return OrderProcessDefinition.builder().id(entity.getId()).name(entity.getName())
                .validationDefinitions(entity.getValidationDefinitions())
                .shippingPreparationDefinition(entity.getShippingPreparationDefinition()).build();
    }

    private OrderProcessDefinitionEntity toPersistenceModel(CreateOrderProcessDefinitionRequest request) {
        return OrderProcessDefinitionEntity.builder().id(UUID.randomUUID().toString())
                .name(request.getName()).validationDefinitions(request.getValidationDefinitions())
                .shippingPreparationDefinition(request.getShippingPreparationDefinition()).build();
    }

}
