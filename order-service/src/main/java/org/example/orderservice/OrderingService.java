package org.example.orderservice;

import java.util.List;

public interface OrderingService {
    void run(String orderProcessDefinition, List<Order> orders);
}
