package org.example.orderservice.api;

import org.example.orderservice.core.OrderProcessDefinitionException;
import org.example.orderservice.process.OrderProcessDefinition;
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
