package org.example.base.repository;

import org.example.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public interface BaseRepository<ID extends Serializable, T extends BaseEntity<ID>> {
    T saveOrUpdate(T entity);
    void removeByID(ID id);
    Optional<T> findById(ID id);
    Collection<T> findAll();
    boolean existByID(ID id);
    long count();
    void commitTransaction();

    void beginTransaction();
}
