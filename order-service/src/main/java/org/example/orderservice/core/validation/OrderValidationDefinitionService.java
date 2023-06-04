package org.example.orderservice.core.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderValidationDefinitionService {

    private IOrderValidationDefinitionStorage storage;

    public OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request) {
        return storage.create(request);
    }

    public OrderValidationDefinition fetch(String definitionId, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException {
        return storage.fetch(definitionId, revisionId);
    }

    public OrderValidationDefinition fetch(String definitionId, Date time) throws OrderValidationDefinitionNotFoundException {
        return storage.fetch(definitionId, time);
    }

    public void edit(String definitionId, EditOrderValidationDefinitionRequest request) throws OrderValidationDefinitionNotFoundException {
        storage.edit(definitionId, request);
    }

    public void delete(String definitionId) throws OrderValidationDefinitionNotFoundException {
        storage.delete(definitionId);
    }

}
