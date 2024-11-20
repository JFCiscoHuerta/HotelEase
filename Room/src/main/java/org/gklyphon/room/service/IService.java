package org.gklyphon.room.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService <T> {
    public T findById(Long id);
    public Page<T> findAll(Pageable pageable);
    public void delete(Long id);
}
