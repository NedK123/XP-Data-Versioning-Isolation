package org.example.orderservice.api.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.CreateOrderValidationDefinitionRequest;
import org.example.orderservice.core.validation.OrderValidationDefinition;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.example.orderservice.core.validation.OrderValidationDefinitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderValidationController implements OrderValidationApi {

    private OrderValidationDefinitionService validationService;

    @Override
    public ResponseEntity<String> create(CreateOrderValidationDefinitionApiRequest request) {
        OrderValidationDefinition definition = validationService.create(CreateOrderValidationDefinitionRequest.builder().build());
        return ResponseEntity.created(URI.create(definition.getId())).build();
    }

    @Override
    public ResponseEntity<OrderValidationDefinition> fetch(String definitionId) throws OrderValidationDefinitionNotFoundException {
        return ResponseEntity.ok(validationService.fetch(definitionId));
    }

    @ExceptionHandler(OrderValidationDefinitionNotFoundException.class)
    public ResponseEntity<String> handle(OrderValidationDefinitionNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

}
