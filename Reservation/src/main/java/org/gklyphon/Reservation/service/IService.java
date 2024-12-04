package org.gklyphon.Reservation.service;

import java.util.List;

/**
 * Generic service interface for basic CRUD operations.
 * <p>
 * This interface defines the contract for basic CRUD operations, which include finding an entity by its ID,
 * retrieving all entities, and deleting an entity by its ID. It is intended to be extended by specific service
 * interfaces that handle different types of entities.
 * </p>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li>{@code findById} - Retrieves an entity by its ID.</li>
 *   <li>{@code findAll} - Retrieves all entities.</li>
 *   <li>{@code deleteById} - Deletes an entity by its ID.</li>
 * </ul>
 *
 * @param <T> the type of the entity to be handled by this service
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 3-Dec-2024
 */
public interface IService <T>{

    /**
     * Finds an entity by its ID.
     * <p>
     * This method retrieves the entity that matches the given {@code id}.
     * </p>
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the given ID, or {@code null} if no entity is found
     */
    public T findById(Long id);

    /**
     * Finds all entities.
     * <p>
     * This method retrieves all entities of type {@code T}.
     * </p>
     *
     * @return a list of all entities
     */
    public List<T> findAll();

    /**
     * Deletes an entity by its ID.
     * <p>
     * This method deletes the entity that matches the given {@code id}.
     * </p>
     *
     * @param id the ID of the entity to delete
     */
    public void deleteById(Long id);
}
