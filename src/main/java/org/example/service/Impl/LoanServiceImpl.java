package org.example.service.Impl;


import org.example.base.service.BaseServiceImpl;
import org.example.entity.*;
import org.example.entity.enumuration.*;
import org.example.repository.LoanRepository;
import org.example.service.BankCardService;
import org.example.service.LoanService;
import org.example.service.StudentService;
import org.example.service.dto.LoanRequest;
import java.util.Collection;
import java.util.List;


public class LoanServiceImpl extends BaseServiceImpl<Integer, Loan, LoanRepository>
                               implements LoanService {

    protected final StudentService studentService;
    protected final BankCardService bankCardService;

    public LoanServiceImpl(LoanRepository repository, StudentService studentService,BankCardService bankCardService) {
        super(repository);
        this.studentService=studentService;
        this.bankCardService=bankCardService;
    }

    @Override
    public Collection<Loan> findAllByNationalCode(String nationalCode) {
        return repository.findAllByNationalCode(nationalCode);
    }

    @Override
    public void applyLoan(LoanRequest loanRequest) {
        Student student = studentService.findByNationalCode(loanRequest.getNationalCode());
            Loan loan = new Loan();
            loan.setLoanGetYear(loanRequest.getYear());
            loanRequest(loanRequest,student,loan);
    }

    @Override
    public long findByBankCard(Loan loan,String cardNumber) {
        return repository.findByBankCard(loan,cardNumber);
    }

    public BankCard extracted(Student student) {
        for (BankCard bankCard: student.getBankCardList()) {
                if (bankCard.getBankName().equals(BankName.REFAH)||bankCard.getBankName().equals(BankName.MASKAN) ||bankCard.getBankName().equals(BankName.MELLI) ||bankCard.getBankName().equals(BankName.TEJARAT)){
                    return bankCard;
                }else{
                    System.out.println("Your card number does not belong to these banks");
                    return null;
                }
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
                loan.setBankCard(extracted(student));
                repository.saveOrUpdate(loan);
                bankCardService.deposit(loan.getAmount(), loan.getBankCard().getCardNumber());
                System.out.println("your request is accepted");
            }else System.out.println("this loan will not be awarded to you");
        }else System.out.println("this loan will not be awarded to you");
    }

    private void processEducationalLoanRequest(Student student,Loan loan) {
        if (repository.findByLoanType(student.getNationalCode(),loan.getLoanGetYear(),LoanType.EDUCATIONAL)==null){
            loan.setStudent(student);
            loan.setLoanType(LoanType.EDUCATIONAL);
            loan.setEducationLevel(student.getEducationLevel());
            loan.setAmount(student.getEducationLevel().getEducational());
            loan.setBankCard(extracted(student));
            repository.saveOrUpdate(loan);
            bankCardService.deposit(loan.getAmount(), loan.getBankCard().getCardNumber());
            System.out.println("your request is accepted");
        }else System.out.println("you get educational loan for this term");
    }

    private void processTuitionLoanRequest(Student student,Loan loan) {
        if (repository.findByLoanType(student.getNationalCode(),loan.getLoanGetYear(),LoanType.TUITION)==null){
            if (!student.getUniversityType().equals(UniversityType.DOLLATI_ROOZANEH)){
                loan.setStudent(student);
                loan.setLoanType(LoanType.TUITION);
                loan.setEducationLevel(student.getEducationLevel());
                loan.setAmount(student.getEducationLevel().getTuition());
                loan.setBankCard(extracted(student));
                repository.saveOrUpdate(loan);
                bankCardService.deposit(loan.getAmount(), loan.getBankCard().getCardNumber());
                System.out.println("your request is accepted");
            }else System.out.println("this loan will not be awarded to you");
        }else System.out.println("you get tuition loan for this term");

    }



}
