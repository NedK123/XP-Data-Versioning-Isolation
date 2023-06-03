package org.example.orderservice.core.process;

import java.util.List;

public interface OrderingService {
    void run(String orderProcessDefinition, List<Order> orders);
}
