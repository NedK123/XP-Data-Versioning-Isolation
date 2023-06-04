package org.example.orderservice.persistence.validation;

import org.example.orderservice.core.validation.OrderValidationDefinitionNotFoundException;

import java.util.Date;

public interface OrderValidationDefinitionEntitiesFetcher {

    /**
     * Returns the latest revision before the specified date
     *
     * @param definitionId
     * @param date
     * @return
     * @throws OrderValidationDefinitionNotFoundException
     */
    OrderValidationDefinitionEntity fetch(String definitionId, Date date) throws OrderValidationDefinitionNotFoundException;

    /**
     * Returns the latest revision
     *
     * @param definitionId
     * @return
     * @throws OrderValidationDefinitionNotFoundException
     */
    OrderValidationDefinitionEntity fetch(String definitionId) throws OrderValidationDefinitionNotFoundException;

    /**
     * Returns the specified revision
     *
     * @param definitionId
     * @param revision
     * @return
     * @throws OrderValidationDefinitionNotFoundException
     */
    OrderValidationDefinitionEntity fetch(String definitionId, int revision) throws OrderValidationDefinitionNotFoundException;

}
