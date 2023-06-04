package org.example.orderservice.persistence;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;

@Service
@Qualifier("JPA")
@AllArgsConstructor
@Slf4j
public class JpaDefinitionFetcher<T> extends AbstractDefinitionEntitiesFetcher<T> {

    private RevisionRepository<T, String, Integer> readRepo;

    @Override
    public T fetch(String definitionId, Date date, Class<T> clazz) throws DefinitionEntityNotFoundException {
        Instant queryInstant = Instant.ofEpochMilli(date.getTime());
        log.info("The query date={}", date);
        return readRepo.findRevisions(definitionId).stream()
                .filter(revision -> revision.getRevisionInstant().get().isBefore(queryInstant)).sorted(Comparator.reverseOrder())
                .findFirst()
                .filter(JpaDefinitionFetcher::isNotADeleteRevision)
                .map(Revision::getEntity)
                .orElseThrow(DefinitionEntityNotFoundException::new);
    }

    @Override
    public T fetch(String definitionId, Class<T> clazz) throws DefinitionEntityNotFoundException {
        return readRepo.findLastChangeRevision(definitionId)
                .filter(JpaDefinitionFetcher::isNotADeleteRevision)
                .map(Revision::getEntity)
                .orElseThrow(DefinitionEntityNotFoundException::new);
    }

    @Override
    public T fetch(String definitionId, int revision, Class<T> clazz) throws DefinitionEntityNotFoundException {
        return readRepo.findRevision(definitionId, revision)
                .map(Revision::getEntity)
                .orElseThrow(DefinitionEntityNotFoundException::new);
    }

    private static <T> boolean isNotADeleteRevision(Revision<Integer, T> s) {
        return !RevisionMetadata.RevisionType.DELETE.equals(s.getMetadata().getRevisionType());
    }

}
