package org.example.service.Impl;


import org.example.base.service.BaseServiceImpl;
import org.example.entity.*;
import org.example.entity.enumuration.*;
import org.example.repository.LoanRepository;
import org.example.service.LoanService;
import org.example.service.StudentService;
import org.example.service.dto.LoanRequest;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class LoanServiceImpl extends BaseServiceImpl<Integer, Loan, LoanRepository>
                               implements LoanService {

    protected final StudentService studentService;


    public LoanServiceImpl(LoanRepository repository, StudentService studentService) {
        super(repository);
        this.studentService=studentService;
    }

    @Override
    public Collection<Loan> findAllByNationalCode(String nationalCode) {
        return repository.findAllByNationalCode(nationalCode);
    }

    @Override
    public void applyLoan(LoanRequest loanRequest,Date currentDate) {
        Student student = studentService.findByNationalCode(loanRequest.getNationalCode());
        if (deactivationLoanRequest(student,currentDate)){
            Loan loan = new Loan();
            loan.setTerm(loanRequest.getTerm());
            loanRequest(loanRequest,student,loan);
        }else System.out.println("You have graduated");
    }

    @Override
    public long findByBankCard(Loan loan,String cardNumber) {
        return repository.findByBankCard(loan,cardNumber);
    }

    private BankCard extracted(Student student, Loan loan) {
        for (BankCard bankCard: student.getBankCardList()) {
                if (bankCard.getBankName().equals("tejarat")||bankCard.getBankName().equals("maskan") ||bankCard.getBankName().equals("melli") ||bankCard.getBankName().equals("refah")){
                    bankCard.setBalance(loan.getAmount());
                    return bankCard;
                }else System.out.println("Your card number does not belong to these banks");
        }
        return null;
    }

    private void loanRequest(LoanRequest loanRequest,Student student,Loan loan) {
        switch (loanRequest.getLoanType()) {
            case TUITION -> processTuitionLoanRequest(student,loan);
            case EDUCATIONAL -> processEducationalLoanRequest(student,loan);
            case HOUSING_DEPOSIT -> processHousingDepositLoanRequest(student,loan);
            default -> throw new IllegalArgumentException("Unsupported loan type: " + loanRequest.getLoanType());
        }
    }

    private void processHousingDepositLoanRequest(Student student,Loan loan) {
        if (repository.findByNationalCodeLoanHousing(student.getNationalCode(),student.getEducationLevel())==null){
            List<String> cities = List.of("rasht","esfahan","shiraz","tabriz","ahvaz","ghom","mashhad","karaj");
            if (student.isMarried()&&
                    repository.findByNationalCodeLoanHousing(student.getSpouseNationalCode(),student.getEducationLevel())==null
                    &&!student.isStayInDorm()){
                loan.setStudent(student);
                loan.setLoanType(LoanType.HOUSING_DEPOSIT);
                loan.setEducationLevel(student.getEducationLevel());
                loan.setHousingRentalNumber(student.getHousingRentalNumber());
                loan.setCity(student.getCity());
                loan.setAddress(student.getAddress());
                if (student.getCity().equals("tehran")) loan.setAmount(32000000);
                else if (cities.contains(student.getCity())) loan.setAmount(26000000);
                else loan.setAmount(19500000);
                loan.setBankCard(extracted(student, loan));
                repository.saveOrUpdate(loan);
                System.out.println("your request is accepted");
            }else System.out.println("this loan will not be awarded to you");
        }else System.out.println("this loan will not be awarded to you");
    }

    private void processEducationalLoanRequest(Student student,Loan loan) {
        if (repository.findByEducational(student.getNationalCode(),loan.getTerm())==null){
            loan.setStudent(student);
            loan.setLoanType(LoanType.EDUCATIONAL);
            loan.setEducationLevel(student.getEducationLevel());
            loan.setAmount(student.getEducationLevel().getEducational());
            loan.setBankCard(extracted(student, loan));
            repository.saveOrUpdate(loan);
            System.out.println("your request is accepted");
        }else System.out.println("you get educational loan for this term");
    }

    private void processTuitionLoanRequest(Student student,Loan loan) {
        if (repository.findByTuition(student.getNationalCode(),loan.getTerm())==null){
            if (!student.getUniversityType().equals(UniversityType.DOLLATI_ROOZANEH)){
                loan.setStudent(student);
                loan.setLoanType(LoanType.TUITION);
                loan.setEducationLevel(student.getEducationLevel());
                loan.setAmount(student.getEducationLevel().getTuition());
                loan.setBankCard(extracted(student, loan));
                repository.saveOrUpdate(loan);
                System.out.println("your request is accepted");
            }else System.out.println("this loan will not be awarded to you");
        }else System.out.println("you get tuition loan for this term");

    }
    private boolean deactivationLoanRequest(Student student,Date currentDate){
        LocalDate localDate = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        switch (student.getEducationLevel()) {
            case ASSOCIATE ,BACHELOR_DISCONTINUOUS , MASTER_DISCONTINUOUS -> {
                if (localDate.getYear()>=student.getEntranceYear()+2){
                    return false;
                }
            }
            case BACHELOR -> {
                if (localDate.getYear()>=student.getEntranceYear()+4) {
                    return false;
                }
            }
//            case BACHELOR_DISCONTINUOUS ->
            case MASTER -> {
                if (localDate.getYear()>=student.getEntranceYear()+6) {
                    return false;
                }
            }
//            case MASTER_DISCONTINUOUS ->
            case DOCTORATE , PHD-> {
                if (localDate.getYear()>=student.getEntranceYear()+5) {
                    return false;
                }
            }
//            case PHD ->
            default -> throw new IllegalArgumentException("Unsupported level type: " + student.getEducationLevel());
        }
        return true;
    }
    @Override
    public void isValidDateToGetLoan(int year,Date currentTime) {
        Calendar instance = Calendar.getInstance();
        instance.set(year,10,23);
        Date date1 = instance.getTime();
        instance.add(Calendar.DATE,7);
        Date date2 = instance.getTime();
        instance.set(year,2,14);
        Date date3 = instance.getTime();
        instance.add(Calendar.DATE,7);
        Date date4 = instance.getTime();
        if (isDateWithinRange(currentTime, date1, date2)) System.out.println("The target date is within the range part1.");
        else if (isDateWithinRange(currentTime, date3, date4)) System.out.println("The target date is within the range part 2.");
        else System.out.println("The target date is outside the range.");
    }
    private boolean isDateWithinRange(Date targetDate, Date startDate, Date endDate) {
        return !(targetDate.before(startDate) || targetDate.after(endDate));
    }


}
