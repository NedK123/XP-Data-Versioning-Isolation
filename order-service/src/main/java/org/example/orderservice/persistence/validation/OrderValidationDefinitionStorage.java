package org.example.orderservice.persistence.validation;

import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderValidationDefinitionStorage implements IOrderValidationDefinitionStorage {

    @Autowired
    private MongoOrderValidationDefinitionRepo repo;

    @Autowired
    @Qualifier("JPA")
    private OrderValidationDefinitionEntitiesFetcher entityFetcher;


    @Override
    public OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request) {
        OrderValidationDefinitionEntity entity = repo.save(toPersistenceModel(request));
        return toDomainModel(entity);
    }

    @Override
    public OrderValidationDefinition fetch(String id, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException {
        if (revisionId.isPresent()) {
            OrderValidationDefinitionEntity entity = entityFetcher.fetch(id, revisionId.get());
            return toDomainModel(entity);
        }
        OrderValidationDefinitionEntity entity = entityFetcher.fetch(id);
        return toDomainModel(entity);
    }

    @Override
    public OrderValidationDefinition fetch(String id, Date date) throws OrderValidationDefinitionNotFoundException {
        OrderValidationDefinitionEntity entity = entityFetcher.fetch(id, date);
        return toDomainModel(entity);
    }

    @Override
    public void edit(String id, EditOrderValidationDefinitionRequest request) throws OrderValidationDefinitionNotFoundException {
        OrderValidationDefinitionEntity definitionEntity = entityFetcher.fetch(id);
        request.ifContainsNameUpdate(definitionEntity::setName);
        request.ifContainsChecksUpdate(s -> definitionEntity.setChecks(s.stream().map(Enum::toString).collect(Collectors.toSet())));
        repo.save(definitionEntity);
    }

    @Override
    public void delete(String definitionId) {
        repo.deleteById(definitionId);
    }

    protected OrderValidationDefinition toDomainModel(OrderValidationDefinitionEntity entity) {
        return OrderValidationDefinition.builder().id(entity.getId()).name(entity.getName()).checks(map(entity)).build();
    }

    private static Set<OrderChecks> map(OrderValidationDefinitionEntity entity) {
        return entity.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet());
    }

    private static OrderValidationDefinitionEntity toPersistenceModel(CreateOrderValidationDefinitionRequest request) {
        return OrderValidationDefinitionEntity.builder().id(UUID.randomUUID().toString())
                .name(request.getName()).checks(map(request)).build();
    }

    private static Set<String> map(CreateOrderValidationDefinitionRequest request) {
        return request.getChecks().stream().map(OrderChecks::toString).collect(Collectors.toSet());
    }

}
