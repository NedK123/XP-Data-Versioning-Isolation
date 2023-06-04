package org.example.orderservice.core.validation;

import org.example.orderservice.persistence.DefinitionEntityNotFoundException;

public class OrderValidationDefinitionNotFoundException extends Exception {
    public OrderValidationDefinitionNotFoundException(DefinitionEntityNotFoundException e) {

    }
}
