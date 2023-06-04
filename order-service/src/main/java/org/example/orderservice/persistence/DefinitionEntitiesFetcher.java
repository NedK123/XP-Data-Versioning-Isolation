package org.example.orderservice.persistence;

import java.util.Date;

public interface DefinitionEntitiesFetcher<T> {

    /**
     * Returns the latest revision before the specified date
     *
     * @param definitionId
     * @param date
     * @param clazz
     * @return
     * @throws DefinitionEntityNotFoundException
     */
    T fetch(String definitionId, Date date, Class<T> clazz) throws DefinitionEntityNotFoundException;

    /**
     * Returns the latest revision
     *
     * @param definitionId
     * @param clazz
     * @return
     * @throws DefinitionEntityNotFoundException
     */
    T fetch(String definitionId, Class<T> clazz) throws DefinitionEntityNotFoundException;

    /**
     * Returns the specified revision
     *
     * @param definitionId
     * @param revision
     * @param clazz
     * @return
     * @throws DefinitionEntityNotFoundException
     */
    T fetch(String definitionId, int revision, Class<T> clazz) throws DefinitionEntityNotFoundException;

}
