package org.example.base.service;

import org.example.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;


public interface BaseService<ID extends Serializable,T extends BaseEntity<ID>> {
    T saveOrUpdate(T entity);
    void remove(ID id);
    T load(ID id);
    Collection<T> loadAll();
    boolean existByID(ID id);
    long count();

}
