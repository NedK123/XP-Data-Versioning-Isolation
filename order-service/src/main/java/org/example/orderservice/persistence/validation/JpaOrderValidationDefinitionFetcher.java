package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

@Service
@Qualifier("JPA")
@AllArgsConstructor
@Slf4j
public class JpaOrderValidationDefinitionFetcher extends AbstractOrderValidationDefinitionEntitiesFetcher {

    private JpaReadOrderValidationDefinitionRepo readRepo;

    @Override
    public OrderValidationDefinitionEntity fetch(String definitionId, Date date) throws OrderValidationDefinitionNotFoundException {
        Instant queryInstant = Instant.ofEpochMilli(date.getTime());
        log.info("The query date={}", date);
        return readRepo.findRevisions(definitionId).stream()
                .filter(revision -> revision.getRevisionInstant().get().isBefore(queryInstant)).sorted(Comparator.reverseOrder())
                .findFirst()
                .filter(JpaOrderValidationDefinitionFetcher::isNotADeleteRevision)
                .map(Revision::getEntity)
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    @Override
    public OrderValidationDefinitionEntity fetch(String definitionId) throws OrderValidationDefinitionNotFoundException {
        return readRepo.findLastChangeRevision(definitionId)
                .filter(JpaOrderValidationDefinitionFetcher::isNotADeleteRevision)
                .map(Revision::getEntity)
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    @Override
    public OrderValidationDefinitionEntity fetch(String definitionId, int revision) throws OrderValidationDefinitionNotFoundException {
        return readRepo.findRevision(definitionId, revision)
                .map(Revision::getEntity)
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

    private static boolean isNotADeleteRevision(Revision<Integer, OrderValidationDefinitionEntity> s) {
        return !RevisionMetadata.RevisionType.DELETE.equals(s.getMetadata().getRevisionType());
    }

}
