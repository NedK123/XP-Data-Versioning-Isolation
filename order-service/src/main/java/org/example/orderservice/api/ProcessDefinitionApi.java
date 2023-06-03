package org.example.orderservice.api;

import org.example.orderservice.core.process.OrderProcessDefinition;
import org.example.orderservice.core.process.OrderProcessDefinitionException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("order/process/definitions")
public interface ProcessDefinitionApi {

    @PostMapping("")
    ResponseEntity<String> create(CreateOrderProcessDefinitionApiRequest request);

    @GetMapping("{id}")
    ResponseEntity<OrderProcessDefinition> fetch(@PathVariable("id") String definitionId) throws OrderProcessDefinitionException;

}
