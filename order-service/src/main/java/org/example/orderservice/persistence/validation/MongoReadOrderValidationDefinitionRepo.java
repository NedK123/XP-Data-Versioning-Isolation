package org.example.orderservice.persistence.validation;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoReadOrderValidationDefinitionRepo extends PagingAndSortingRepository<OrderValidationDefinitionEntity, String> {
    Optional<OrderValidationDefinitionEntity> findById(String id);
}
