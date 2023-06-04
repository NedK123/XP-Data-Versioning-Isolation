package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.persistence.DefinitionEntityNotFoundException;
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
public class AuditReaderDefinitionEntitiesFetcher<T> extends AbstractDefinitionEntitiesFetcher<T> {

    private AuditReader auditReader;

    @Override
    public T fetch(String definitionId, Date date) throws DefinitionEntityNotFoundException {
        log.info("The query date={}", date);
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(OrderValidationDefinitionEntity.class, true, false);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.add(AuditEntity.revisionProperty("timestamp").le(date.getTime()));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = runQuery(maxRevisionNumberQuery).map(s -> (int) s).orElseThrow(DefinitionEntityNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, maxRevisionNumber);
        return runQuery(auditQuery).map(s -> (T) s).orElseThrow(DefinitionEntityNotFoundException::new);
    }

    @Override
    public T fetch(String definitionId) throws DefinitionEntityNotFoundException {
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(OrderValidationDefinitionEntity.class, true, true);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = runQuery(maxRevisionNumberQuery).map(s -> (int) s).orElseThrow(DefinitionEntityNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, maxRevisionNumber);
        return runQuery(auditQuery).map(s -> (T) s).orElseThrow(DefinitionEntityNotFoundException::new);
    }

    @Override
    public T fetch(String definitionId, int revision) throws DefinitionEntityNotFoundException {
        AuditQuery query = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, revision);
        return runQuery(query).map(result -> (T) result).orElseThrow(DefinitionEntityNotFoundException::new);
    }

    private static Optional<Object> runQuery(AuditQuery auditQuery) {
        try {
            return Optional.ofNullable(auditQuery.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
