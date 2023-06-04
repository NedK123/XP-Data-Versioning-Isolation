package org.example.orderservice.persistence.validation;

import org.example.orderservice.core.validation.OrderValidationDefinition;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;

import java.util.Date;

public interface OrderValidationDefinitionEntitiesFetcher {

    OrderValidationDefinition fetch(String definitionId, Date date) throws OrderValidationDefinitionNotFoundException;

}
