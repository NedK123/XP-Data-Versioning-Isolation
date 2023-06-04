package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.OrderValidationDefinition;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Qualifier("AuditQuery")
@AllArgsConstructor
@Slf4j
public class AuditQueryOrderValidationDefinitionEntitiesFetcher extends AbstractOrderValidationDefinitionEntitiesFetcher {

    private AuditReader auditReader;

    @Override
    public OrderValidationDefinition fetch(String definitionId, Date date) throws OrderValidationDefinitionNotFoundException {
        log.info("The query date={}", date);
        AuditQuery maxRevisionNumberQuery = auditReader.createQuery().forRevisionsOfEntity(OrderValidationDefinitionEntity.class, true, false);
        maxRevisionNumberQuery.add(AuditEntity.id().eq(definitionId));
        maxRevisionNumberQuery.add(AuditEntity.revisionProperty("timestamp").le(date.getTime()));
        maxRevisionNumberQuery.addProjection(AuditEntity.revisionNumber().max());
        int maxRevisionNumber = Optional.ofNullable(maxRevisionNumberQuery.getSingleResult()).map(s -> (int) s).orElseThrow(OrderValidationDefinitionNotFoundException::new);
        AuditQuery auditQuery = auditReader.createQuery().forEntitiesAtRevision(OrderValidationDefinitionEntity.class, maxRevisionNumber);
        OrderValidationDefinitionEntity singleResult = (OrderValidationDefinitionEntity) auditQuery.getSingleResult();
        return toDomainModel(singleResult);

    }

}
