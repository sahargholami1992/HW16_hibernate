package org.example.service;

import org.example.base.service.BaseService;
import org.example.entity.Student;
import org.example.service.dto.StudentRegistrationDTO;


public interface StudentService extends BaseService<Integer, Student> {
    String studentRegister(StudentRegistrationDTO dto);
    Student findByNationalCode(String nationalCode);
    boolean studentLogIn(String userName,String password);

}
