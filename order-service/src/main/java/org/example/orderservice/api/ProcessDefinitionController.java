package org.example.orderservice.api;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.process.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<OrderProcessDefinition> fetch(String definitionId) throws OrderProcessDefinitionNotFoundException {
        OrderProcessDefinition definition = processDefinitionService.fetch(definitionId);
        return ResponseEntity.ok(definition);
    }

    @Override
    public ResponseEntity<Void> edit(String definitionId, EditOrderProcessDefinitionApiRequest request) throws OrderProcessDefinitionNotFoundException {
        processDefinitionService.edit(definitionId, buildEditRequest(request));
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(OrderProcessDefinitionNotFoundException.class)
    public ResponseEntity<String> handle(OrderProcessDefinitionNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    private static EditOrderProcessDefinitionRequest buildEditRequest(EditOrderProcessDefinitionApiRequest request) {
        EditOrderProcessDefinitionRequest.EditOrderProcessDefinitionRequestBuilder builder = EditOrderProcessDefinitionRequest.builder();
        if (Objects.nonNull(request.getName())) {
            builder.name(Optional.of(request.getName()));
        }
        if (Objects.nonNull(request.getValidationDefinitions())) {
            builder.validationDefinitions(Optional.of(request.getValidationDefinitions()));
        }
        if (Objects.nonNull(request.getShippingPreparationDefinition())) {
            builder.shippingPreparationDefinition(Optional.of(request.getShippingPreparationDefinition()));
        }
        return builder.build();
    }

    private static URI resourceLocation(OrderProcessDefinition processDefinition) {
        return URI.create(processDefinition.getId());
    }

    private CreateOrderProcessDefinitionRequest map(CreateOrderProcessDefinitionApiRequest request) {
        return CreateOrderProcessDefinitionRequest.builder().validationDefinitions(request.getValidationDefinitions())
                .name(request.getName()).shippingPreparationDefinition(request.getShippingPreparationDefinition()).build();
    }

}
