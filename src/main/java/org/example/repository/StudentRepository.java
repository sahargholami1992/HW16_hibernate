package org.example.repository;

import org.example.base.repository.BaseRepository;
import org.example.entity.Student;

public interface StudentRepository extends BaseRepository<Integer, Student> {
    Student findByNationalCode(String nationalCode);
    boolean existByNationalCode(String nationalCode, String password);
}
