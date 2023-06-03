package org.example.orderservice.api;

import org.example.orderservice.core.process.OrderProcessDefinition;
import org.example.orderservice.core.process.OrderProcessDefinitionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("order/process/definitions")
public interface ProcessDefinitionApi {

    @PostMapping("")
    ResponseEntity<String> create(CreateOrderProcessDefinitionApiRequest request);

    @GetMapping("{id}")
    ResponseEntity<OrderProcessDefinition> fetch(@PathVariable("id") String definitionId) throws OrderProcessDefinitionNotFoundException;

    @PutMapping("{id}")
    ResponseEntity<Void> edit(String definitionId, EditOrderProcessDefinitionApiRequest request) throws OrderProcessDefinitionNotFoundException;

}
