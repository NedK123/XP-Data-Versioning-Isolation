package org.example.orderservice.api.validation;

import org.example.orderservice.core.validation.OrderValidationDefinition;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RequestMapping("order/validation/")
public interface OrderValidationApi {

    @PostMapping("definitions/")
    ResponseEntity<String> create(CreateOrderValidationDefinitionApiRequest request);

    @GetMapping("definitions/{id}")
    ResponseEntity<OrderValidationDefinition> fetch(@PathVariable("id") String definitionId, @RequestParam("revision") Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException;

    @GetMapping("definitions/{id}/time")
    ResponseEntity<OrderValidationDefinition> fetch(@PathVariable("id") String definitionId, @RequestParam(value = "mostRecentAtDate", defaultValue = "2023-07-07 14:50:20") String mostRecentAtDate) throws OrderValidationDefinitionNotFoundException, ParseException;

    @PutMapping("definitions/{id}")
    ResponseEntity<Void> edit(String definitionId, EditOrderValidationDefinitionApiRequest request) throws OrderValidationDefinitionNotFoundException;

    @DeleteMapping("definitions/{id}")
    ResponseEntity<Void> delete(String definitionId) throws OrderValidationDefinitionNotFoundException;

}
