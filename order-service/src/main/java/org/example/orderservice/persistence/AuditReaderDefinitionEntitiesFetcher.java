package org.example.orderservice.persistence;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Qualifier("AuditReader")
@Slf4j
public class AuditReaderDefinitionEntitiesFetcher<T> extends AbstractDefinitionEntitiesFetcher<T> {

    @Autowired
    private AuditReader auditReader;

    @Override
    public T fetch(String definitionId, Date date, Class<T> clazz) throws DefinitionEntityNotFoundException {
        log.info("The query date={}", date);
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(clazz, true, false);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.add(AuditEntity.revisionProperty("timestamp").le(date.getTime()));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = runQuery(maxRevisionNumberQuery).map(s -> (int) s).orElseThrow(DefinitionEntityNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(clazz, maxRevisionNumber);
        return runQuery(auditQuery).map(s -> (T) s).orElseThrow(DefinitionEntityNotFoundException::new);
    }

    @Override
    public T fetch(String definitionId, Class<T> clazz) throws DefinitionEntityNotFoundException {
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(clazz, true, true);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = runQuery(maxRevisionNumberQuery).map(s -> (int) s).orElseThrow(DefinitionEntityNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(clazz, maxRevisionNumber);
        return runQuery(auditQuery).map(s -> (T) s).orElseThrow(DefinitionEntityNotFoundException::new);
    }

    @Override
    public T fetch(String definitionId, int revision, Class<T> clazz) throws DefinitionEntityNotFoundException {
        AuditQuery query = auditReader.createQuery().forEntitiesAtRevision(clazz, revision);
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
