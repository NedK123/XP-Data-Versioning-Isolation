package org.example.orderservice.persistence.process;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProcessDefinitionRepo extends CrudRepository<OrderProcessDefinitionEntity, String> {
}
