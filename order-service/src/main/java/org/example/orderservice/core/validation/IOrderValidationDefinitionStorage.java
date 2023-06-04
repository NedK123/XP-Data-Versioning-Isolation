package org.example.orderservice.core.validation;

import java.util.Date;
import java.util.Optional;

public interface IOrderValidationDefinitionStorage {

    OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request);

    OrderValidationDefinition fetch(String definitionId, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException;

    OrderValidationDefinition fetch(String definitionId, Date time) throws OrderValidationDefinitionNotFoundException;

    void edit(String definitionId, EditOrderValidationDefinitionRequest request) throws OrderValidationDefinitionNotFoundException;

    void delete(String definitionId) throws OrderValidationDefinitionNotFoundException;

}
