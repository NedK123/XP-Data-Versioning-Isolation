package org.example.orderservice.core.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderValidationDefinitionService {

    private OrderValidationDefinitionStorage storage;

    public OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request) {
        return storage.create(request);
    }

    public OrderValidationDefinition fetch(String definitionId) throws OrderValidationDefinitionNotFoundException {
        return storage.fetch(definitionId);
    }

}
