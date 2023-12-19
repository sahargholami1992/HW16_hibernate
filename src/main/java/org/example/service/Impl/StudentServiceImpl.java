package org.example.service.Impl;

import org.example.base.service.BaseServiceImpl;
import org.example.entity.Student;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.example.service.dto.StudentRegistrationDTO;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class StudentServiceImpl extends BaseServiceImpl<Integer, Student, StudentRepository>
                               implements StudentService {
    public StudentServiceImpl(StudentRepository repository) {
        super(repository);
    }

    @Override
    public void studentRegister(StudentRegistrationDTO dto) {
        if (isValidNationalCodeWithRegex(dto.getNationalCode())){
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
            student.setCurrentTerm(dto.getCurrentTerm());
            student.setEducationLevel(dto.getEducationLevel());
            student.setMarried(dto.isMarried());
            student.setSpouseNationalCode(dto.getSpouseNationalCode());
            student.setCity(dto.getCity());
            student.setStayInDorm(dto.isStayInDorm());
            student.setHousingRentalNumber(dto.getHousingRentalNumber());
            student.setAddress(dto.getAddress());
            student.setPostalCode(dto.getPostalCode());
            student.setPassword(generatePassword());
            repository.saveOrUpdate(student);
        }else throw new RuntimeException("invalid nationalCode");
    }
//    private void validateRegistrationDTO(StudentRegistrationDTO dto) {
//        if (StringUtils.isBlank(dto.getMobileNumber())) {
//            throw new RuntimeException("empty mobileNumber");
//        }
//        if (StringUtils.isBlank(dto.getPassword())) {
//            throw new RuntimeException("empty password");
//        }
//        if (dto.getNationalCode().length() != 10 || !dto.getNationalCode().startsWith("09")) {
//            throw new RuntimeException("invalid mobileNumber");
//        }else {
//            Pattern pattern =
//                    Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
//            dto.getNationalCode().matches(pattern.pattern());
//        }
//        if (dto.getPassword().length() < 8 || dto.getPassword().length() > 16) {
//            throw new RuntimeException("password length in invalid");
//        }
//
//        }
    public boolean isValidNationalCodeWithRegex(String nationalCode) {
        Pattern pattern =
                Pattern.compile("^[0-9]{10}$");
        return nationalCode.matches(pattern.pattern());
    }
    private String generatePassword() {
         String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&])(?=\\S+$).{8,}$";
        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        while (!password.toString().matches(passwordPattern)) {
            password.setLength(0);  // Clear the StringBuilder

            // Generate your password logic here
            // Example: Append random characters to the password
            for (int i = 0; i < 12; i++) {
                char randomChar = (char) (random.nextInt(94) + 33); // ASCII printable characters
                password.append(randomChar);
            }
        }

        return password.toString();
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
