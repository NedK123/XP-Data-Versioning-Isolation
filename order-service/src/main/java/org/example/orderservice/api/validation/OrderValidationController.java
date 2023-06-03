package org.example.orderservice.api.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class OrderValidationController implements OrderValidationApi {

    private OrderValidationDefinitionService validationService;

    @Override
    public ResponseEntity<String> create(CreateOrderValidationDefinitionApiRequest request) {
        OrderValidationDefinition definition = validationService.create(map(request));
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

    private static CreateOrderValidationDefinitionRequest map(CreateOrderValidationDefinitionApiRequest request) {
        return CreateOrderValidationDefinitionRequest.builder().checks(request.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet())).build();
    }

}
