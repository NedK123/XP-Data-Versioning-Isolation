package org.example.orderservice.core.process;

public interface OrderProcessDefinitionStorage {

    OrderProcessDefinition create(CreateOrderProcessDefinitionRequest request);

    OrderProcessDefinition fetch(String id) throws OrderProcessDefinitionNotFoundException;

    void edit(String id, EditOrderProcessDefinitionRequest request) throws OrderProcessDefinitionNotFoundException;

}
