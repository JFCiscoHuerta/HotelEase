package org.gklyphon.room.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic service interface for basic CRUD operations.
 * This interface provides common methods to perform CRUD operations
 * on entities of type {@code T}.
 *
 * @param <T> the type of the entity that the service manages
 */
public interface IService <T> {

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity to be found
     * @return the entity of type {@code T} if found, otherwise {@code null}
     */
    public T findById(Long id);

    /**
     * Retrieves a paginated list of all entities of type {@code T}.
     *
     * @param pageable pagination details (page number, size, etc.)
     * @return a {@link Page} of entities of type {@code T}
     */
    public Page<T> findAllPageable(Pageable pageable);

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to be deleted
     */
    public void delete(Long id);
}
