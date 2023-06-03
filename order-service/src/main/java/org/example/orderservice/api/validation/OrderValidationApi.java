package org.example.orderservice.api.validation;

import org.example.orderservice.core.validation.OrderValidationDefinition;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("order/validation/")
public interface OrderValidationApi {

    @PostMapping("definitions/")
    ResponseEntity<String> create(CreateOrderValidationDefinitionApiRequest request);

    @GetMapping("definitions/{id}")
    ResponseEntity<OrderValidationDefinition> fetch(@PathVariable("id") String definitionId) throws OrderValidationDefinitionNotFoundException, OrderValidationDefinitionNotFoundException;

}
