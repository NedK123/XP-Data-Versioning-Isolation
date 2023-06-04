package org.example.orderservice.persistence.validation;

import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoReadOrderValidationDefinitionRepo extends RevisionRepository<OrderValidationDefinitionEntity, String, Integer> {

}
