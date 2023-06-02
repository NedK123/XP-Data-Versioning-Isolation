package org.example.orderservice.core;

import org.example.orderservice.process.OrderProcessDefinition;

public interface OrderProcessDefinitionStorage {

    OrderProcessDefinition create(CreateOrderProcessDefinitionRequest request);

    OrderProcessDefinition fetch(String id) throws OrderProcessDefinitionException;

}
