package org.example.orderservice.core.validation;

import java.util.Date;
import java.util.Optional;

public interface OrderValidationDefinitionStorage {

    OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request);

    OrderValidationDefinition fetch(String id, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException;

    OrderValidationDefinition fetch(String id, Date time) throws OrderValidationDefinitionNotFoundException;

    void edit(String id, EditOrderValidationDefinitionRequest request) throws OrderValidationDefinitionNotFoundException;

}
