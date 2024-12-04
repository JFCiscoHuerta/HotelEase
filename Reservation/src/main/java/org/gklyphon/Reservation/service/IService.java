package org.gklyphon.Reservation.service;

import java.util.List;

public interface IService <T>{
    public T findById(Long id);
    public List<T> findAll();
    public void deleteById(Long id);
}
