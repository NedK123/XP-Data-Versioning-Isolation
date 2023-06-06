package org.example.orderservice.persistence.validation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresOrderValidationDefinitionRepo extends CrudRepository<OrderValidationDefinitionEntity, String> {
}
