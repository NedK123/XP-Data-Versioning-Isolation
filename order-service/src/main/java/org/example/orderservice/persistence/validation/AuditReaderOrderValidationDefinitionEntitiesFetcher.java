package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Qualifier("AuditReader")
@AllArgsConstructor
@Slf4j
public class AuditReaderOrderValidationDefinitionEntitiesFetcher extends AbstractOrderValidationDefinitionEntitiesFetcher {

    private AuditReader auditReader;

    @Override
    public OrderValidationDefinitionEntity fetch(String definitionId, Date date) throws OrderValidationDefinitionNotFoundException {
        log.info("The query date={}", date);
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(OrderValidationDefinitionEntity.class, true, false);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.add(AuditEntity.revisionProperty("timestamp").le(date.getTime()));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = runQuery(maxRevisionNumberQuery).map(s -> (int) s).orElseThrow(OrderValidationDefinitionNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, maxRevisionNumber);
        return runQuery(auditQuery).map(s -> (OrderValidationDefinitionEntity) s)
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    @Override
    public OrderValidationDefinitionEntity fetch(String definitionId) throws OrderValidationDefinitionNotFoundException {
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(OrderValidationDefinitionEntity.class, true, true);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = runQuery(maxRevisionNumberQuery).map(s -> (int) s).orElseThrow(OrderValidationDefinitionNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, maxRevisionNumber);
        return runQuery(auditQuery).map(s -> (OrderValidationDefinitionEntity) s)
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    @Override
    public OrderValidationDefinitionEntity fetch(String definitionId, int revision) throws OrderValidationDefinitionNotFoundException {
        AuditQuery query = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, revision);
        return runQuery(query)
                .map(result -> (OrderValidationDefinitionEntity) result)
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    private static Optional<Object> runQuery(AuditQuery auditQuery) {
        try {
            return Optional.ofNullable(auditQuery.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
