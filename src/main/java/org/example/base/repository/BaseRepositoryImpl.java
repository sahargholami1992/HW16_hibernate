package org.example.base.repository;

import lombok.RequiredArgsConstructor;
import org.example.base.entity.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
@RequiredArgsConstructor
public abstract class BaseRepositoryImpl<ID extends Serializable,T extends BaseEntity<ID>> implements BaseRepository<ID,T>{
    protected final EntityManager entityManager;
    @Override
    public T saveOrUpdate(T entity) {
        if (entity.getId()==null) entityManager.persist(entity);
        else entity = entityManager.merge(entity);
        return entity;
    }

    @Override
    public void removeByID(ID id) {
        entityManager.createQuery(
                "delete from "+getEntityClass().getSimpleName()+" e where e.id = :id"
                ,getEntityClass());
    }

    protected abstract Class<T> getEntityClass();

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(
                entityManager.find(getEntityClass(),id));
    }

    @Override
    public Collection<T> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public boolean existByID(ID id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<T> root = query.from(getEntityClass());
        query.select(criteriaBuilder.count(root));
        query.where(criteriaBuilder.equal(root.get("id"),id));
        return entityManager.createQuery(query).getSingleResult()>0;
    }

    @Override
    public long count() {
        return entityManager.createQuery(
                "select count(e) from " + getEntityClass().getSimpleName() + " e",
                Long.class
        ).getSingleResult();
    }
}
