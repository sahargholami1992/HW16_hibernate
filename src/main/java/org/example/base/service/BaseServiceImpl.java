package org.example.base.service;

import lombok.RequiredArgsConstructor;
import org.example.base.entity.BaseEntity;
import org.example.base.repository.BaseRepository;

import java.io.Serializable;
import java.util.Collection;


@RequiredArgsConstructor
public class BaseServiceImpl<ID extends Serializable, T extends BaseEntity<ID>,
        R extends BaseRepository<ID,T>>
                          implements BaseService<ID,T> {

    protected final R repository;

    @Override
    public T saveOrUpdate(T entity) {
        return repository.saveOrUpdate(entity);
    }

    @Override
    public void remove(ID id) {
        repository.removeByID(id);
    }

    @Override
    public T load(ID id) {
       return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<T> loadAll() {
        return repository.findAll();
    }

    @Override
    public boolean existByID(ID id) {
        return repository.existByID(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
