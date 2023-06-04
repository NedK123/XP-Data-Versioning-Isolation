package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.*;
import org.springframework.data.history.Revision;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MongoOrderValidationDefinitionStorage implements OrderValidationDefinitionStorage {

    private MongoOrderValidationDefinitionRepo repo;
    private MongoReadOrderValidationDefinitionRepo readRepo;

    @Override
    public OrderValidationDefinition create(CreateOrderValidationDefinitionRequest request) {
        OrderValidationDefinitionEntity entity = repo.save(toPersistenceModel(request));
        return toDomainModel(entity);
    }

    @Override
    public OrderValidationDefinition fetch(String id, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException {
        Revision<Integer, OrderValidationDefinitionEntity> entityRevision = findOrderValidationDefinitionEntity(id, revisionId);
        return toDomainModel(entityRevision.getEntity());
    }

    @Override
    public OrderValidationDefinition fetch(String id, Date time) throws OrderValidationDefinitionNotFoundException {
        Instant queryInstant = Instant.ofEpochMilli(time.getTime());
        log.info("The query date={}", queryInstant);
        return readRepo.findRevisions(id).stream()
                .filter(revision -> revision.getRevisionInstant().get().isBefore(queryInstant)).sorted(Comparator.reverseOrder())
                .findFirst().map(revision -> toDomainModel(revision.getEntity()))
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    @Override
    public void edit(String id, EditOrderValidationDefinitionRequest request) throws OrderValidationDefinitionNotFoundException {
        Revision<Integer, OrderValidationDefinitionEntity> revision = readRepo.findLastChangeRevision(id).orElseThrow(OrderValidationDefinitionNotFoundException::new);
        OrderValidationDefinitionEntity definitionEntity = revision.getEntity();
        request.ifContainsNameUpdate(definitionEntity::setName);
        request.ifContainsChecksUpdate(s -> definitionEntity.setChecks(s.stream().map(Enum::toString).collect(Collectors.toSet())));
        repo.save(definitionEntity);
    }

    private Revision<Integer, OrderValidationDefinitionEntity> findOrderValidationDefinitionEntity(String id, Optional<Integer> revisionId) throws OrderValidationDefinitionNotFoundException {
        if (revisionId.isPresent()) {
            return readRepo.findRevision(id, revisionId.get()).orElseThrow(OrderValidationDefinitionNotFoundException::new);
        }
        return readRepo.findLastChangeRevision(id).orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    private static OrderValidationDefinitionEntity toPersistenceModel(CreateOrderValidationDefinitionRequest request) {
        return OrderValidationDefinitionEntity.builder().id(UUID.randomUUID().toString())
                .name(request.getName()).checks(map(request)).build();
    }

    private static Set<String> map(CreateOrderValidationDefinitionRequest request) {
        return request.getChecks().stream().map(OrderChecks::toString).collect(Collectors.toSet());
    }

    private OrderValidationDefinition toDomainModel(OrderValidationDefinitionEntity entity) {
        return OrderValidationDefinition.builder().id(entity.getId()).name(entity.getName()).checks(map(entity)).build();
    }

    private static Set<OrderChecks> map(OrderValidationDefinitionEntity entity) {
        return entity.getChecks().stream().map(OrderChecks::valueOf).collect(Collectors.toSet());
    }

}
