package org.example.orderservice.persistence.process;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderProcessDefinitionRepo extends MongoRepository<OrderProcessDefinitionEntity, String> {
}
