package org.example.orderservice.core.process;

public interface OrderProcessDefinitionStorage {

    OrderProcessDefinition create(CreateOrderProcessDefinitionRequest request);

    OrderProcessDefinition fetch(String id) throws OrderProcessDefinitionException;

}
