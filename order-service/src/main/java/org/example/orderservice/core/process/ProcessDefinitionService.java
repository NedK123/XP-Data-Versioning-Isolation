package org.example.orderservice.core.process;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessDefinitionService {

    private OrderProcessDefinitionStorage storage;

    public OrderProcessDefinition create(CreateOrderProcessDefinitionRequest request) {
        return storage.create(request);
    }

    public OrderProcessDefinition fetch(String id) throws OrderProcessDefinitionException {
        return storage.fetch(id);
    }

}
