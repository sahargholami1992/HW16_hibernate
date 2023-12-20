package org.example.service.Impl;

import org.example.base.service.BaseServiceImpl;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.example.service.dto.StudentRegistrationDTO;
import org.example.utill.Validation;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class StudentServiceImpl extends BaseServiceImpl<Integer, Student, StudentRepository>
                               implements StudentService {
    public StudentServiceImpl(StudentRepository repository) {
        super(repository);
    }

    @Override
    public String studentRegister(StudentRegistrationDTO dto) {
        if (Validation.isValidNationalCodeWithRegex(dto.getNationalCode())){
            Student student = new Student();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setFatherName(dto.getFatherName());
            student.setMotherName(dto.getMotherName());
            student.setIdentityNumber(dto.getMotherName());
            student.setNationalCode(dto.getNationalCode());
            student.setBirthDate(dto.getBirthDate());
            student.setStudentId(dto.getStudentId());
            student.setUniversityName(dto.getUniversityName());
            student.setUniversityType(dto.getUniversityType());
            student.setEntranceYear(dto.getEntranceYear());
            student.setEducationLevel(dto.getEducationLevel());
            student.setMarried(dto.isMarried());
            student.setSpouseNationalCode(dto.getSpouseNationalCode());
            student.setCity(dto.getCity());
            student.setStayInDorm(dto.isStayInDorm());
            student.setHousingRentalNumber(dto.getHousingRentalNumber());
            student.setAddress(dto.getAddress());
            student.setPostalCode(dto.getPostalCode());
            student.setPassword(Validation.generatePassword());
            repository.saveOrUpdate(student);
            return student.getPassword();
        }else throw new RuntimeException("invalid nationalCode");
    }
    @Override
    public Student findByNationalCode(String nationalCode) {
        return repository.findByNationalCode(nationalCode);
    }

    @Override
    public boolean studentLogIn(String userName,String password) {
       return repository.existByNationalCode(userName,password);
    }
}
