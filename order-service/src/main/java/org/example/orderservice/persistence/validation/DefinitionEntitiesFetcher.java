package org.example.orderservice.persistence.validation;

import org.example.orderservice.persistence.DefinitionEntityNotFoundException;

import java.util.Date;

public interface DefinitionEntitiesFetcher<T> {

    /**
     * Returns the latest revision before the specified date
     *
     * @param definitionId
     * @param date
     * @return T
     * @throws DefinitionEntityNotFoundException
     */
    T fetch(String definitionId, Date date) throws DefinitionEntityNotFoundException;

    /**
     * Returns the latest revision
     *
     * @param definitionId
     * @return T
     * @throws DefinitionEntityNotFoundException
     */
    T fetch(String definitionId) throws DefinitionEntityNotFoundException;

    /**
     * Returns the specified revision
     *
     * @param definitionId
     * @param revision
     * @return T
     * @throws DefinitionEntityNotFoundException
     */
    T fetch(String definitionId, int revision) throws DefinitionEntityNotFoundException;

}
