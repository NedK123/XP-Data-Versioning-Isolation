package org.example.orderservice.persistence.validation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.validation.OrderValidationDefinition;
import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

@Service
@Qualifier("JPA")
@AllArgsConstructor
@Slf4j
public class JpaOrderValidationDefinitionFetcher extends AbstractOrderValidationDefinitionEntitiesFetcher {

    private MongoReadOrderValidationDefinitionRepo readRepo;

    @Override
    public OrderValidationDefinition fetch(String definitionId, Date date) throws OrderValidationDefinitionNotFoundException {
        Instant queryInstant = Instant.ofEpochMilli(date.getTime());
        log.info("The query date={}", date);
        return readRepo.findRevisions(definitionId).stream()
                .filter(revision -> revision.getRevisionInstant().get().isBefore(queryInstant)).sorted(Comparator.reverseOrder())
                .findFirst().map(revision -> toDomainModel(revision.getEntity()))
                .orElseThrow(OrderValidationDefinitionNotFoundException::new);
    }

}
