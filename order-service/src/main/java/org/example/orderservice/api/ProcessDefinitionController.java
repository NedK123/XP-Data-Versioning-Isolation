package org.example.orderservice.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.process.CreateOrderProcessDefinitionRequest;
import org.example.orderservice.core.process.OrderProcessDefinition;
import org.example.orderservice.core.process.OrderProcessDefinitionException;
import org.example.orderservice.core.process.ProcessDefinitionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@AllArgsConstructor
@Slf4j
public class ProcessDefinitionController implements ProcessDefinitionApi {

    private ProcessDefinitionService processDefinitionService;

    @Override
    public ResponseEntity<String> create(CreateOrderProcessDefinitionApiRequest request) {
        OrderProcessDefinition processDefinition = processDefinitionService.create(map(request));
        return ResponseEntity.created(resourceLocation(processDefinition)).build();
    }

    @Override
    public ResponseEntity<OrderProcessDefinition> fetch(String definitionId) throws OrderProcessDefinitionException {
        OrderProcessDefinition definition = processDefinitionService.fetch(definitionId);
        return ResponseEntity.ok(definition);
    }

    @ExceptionHandler(OrderProcessDefinitionException.class)
    public ResponseEntity<String> handle(OrderProcessDefinitionException ex) {
        return ResponseEntity.notFound().build();
    }

    private static URI resourceLocation(OrderProcessDefinition processDefinition) {
        return URI.create(processDefinition.getId());
    }

    private CreateOrderProcessDefinitionRequest map(CreateOrderProcessDefinitionApiRequest request) {
        return CreateOrderProcessDefinitionRequest.builder().validationDefinitions(request.getValidationDefinitions())
                .shippingPreparationDefinition(request.getShippingPreparationDefinition()).build();
    }

}
