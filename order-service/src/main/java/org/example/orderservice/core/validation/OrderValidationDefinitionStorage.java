package org.example.orderservice.core.validation;

public interface OrderValidationDefinitionStorage {

    OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request);

    OrderValidationDefinition fetch(String id) throws OrderValidationDefinitionNotFoundException;

}
