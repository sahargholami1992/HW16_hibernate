package org.example.repository.impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.entity.Student;
import org.example.repository.StudentRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class StudentRepositoryImpl extends BaseRepositoryImpl<Integer, Student>
                                 implements StudentRepository {

    public StudentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    public Student findByNationalCode(String nationalCode) {
        try {
            TypedQuery<Student> query = entityManager.createQuery(
                    "select s from Student s where s.nationalCode = :nationalCode", Student.class);
            query.setParameter("nationalCode",nationalCode);
            return query.getSingleResult();
        }catch (Exception e){
            System.out.println(new IllegalArgumentException(" user name not found").getMessage());
            return null;
        }

    }

    @Override
    public boolean existByNationalCode(String nationalCode, String password) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "select count(s) from Student s where s.nationalCode = :nationalCode and s.password = :password", Long.class);
            query.setParameter("nationalCode",nationalCode);
            query.setParameter("password",password);
            return query.getSingleResult()>0;
        }catch (Exception e){
            System.out.println(new IllegalArgumentException(" user name not found").getMessage());
            return false;
        }

    }
}
