package org.example.orderservice.core.process;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderProcessService implements OrderingService {

    @Override
    public void run(String orderProcessDefinition, List<Order> orders) {

    }

}
