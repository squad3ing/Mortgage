package com.ing.mortgage.service;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.mortgage.dto.AccountDTO;
import com.ing.mortgage.dto.MortgageRequsetDTO;
import com.ing.mortgage.dto.MortgageResponseDTO;
import com.ing.mortgage.entity.Account;
import com.ing.mortgage.entity.Customer;
import com.ing.mortgage.entity.Mortgage;
import com.ing.mortgage.entity.Transaction;
import com.ing.mortgage.exception.CustomError;
import com.ing.mortgage.exception.CustomerAccountNotFound;
import com.ing.mortgage.repository.AccountRepository;
import com.ing.mortgage.repository.CustomerRepository;
import com.ing.mortgage.repository.MortgageRepository;

@Service
public class MortgageServiceImpl implements MortgageService {

	@Autowired
	MortgageRepository mortgageRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(MortgageServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AccountRepository accountRepository;

	public MortgageResponseDTO createMortgage(MortgageRequsetDTO mortgageRequsetDTO) {
		
		Customer customer = new Customer();
		customer.setLoginId(mortgageRequsetDTO.getFirstName() + "25");
		customer.setPassword("Hcl@123");
		customer.setCustomerName(mortgageRequsetDTO.getFirstName());
		customerRepository.save(customer);
		LOGGER.info("saved");
		
		Account transactionalAccount;
		Account mortgageAccount;

		List<Transaction> transactions = new ArrayList<>();
		if (mortgageRequsetDTO.getPropertyCost() >= 100000 && mortgageRequsetDTO.getDeposit() > 0) {
			 transactionalAccount = new Account();
			transactionalAccount.setBalance(mortgageRequsetDTO.getPropertyCost() - mortgageRequsetDTO.getDeposit());
			transactionalAccount.setAccountNumber("ACC25");
			transactionalAccount.setAccountType("Transactional Account");
			transactionalAccount.setDate(LocalDate.now());
			transactionalAccount.setCustomer(customer);
			transactionalAccount.setTransactions(transactions);
			accountRepository.save(transactionalAccount);

			 mortgageAccount = new Account();
			mortgageAccount.setBalance(-(mortgageRequsetDTO.getPropertyCost() - mortgageRequsetDTO.getDeposit()));
			mortgageAccount.setAccountNumber("MORT25");
			mortgageAccount.setAccountType("Mortgage Account");
			mortgageAccount.setDate(LocalDate.now());
			mortgageAccount.setCustomer(customer);
			mortgageAccount.setTransactions(transactions);
			accountRepository.save(mortgageAccount);
		} 
		
		else {
			throw new CustomError("Please provide property cost above 100000€ ");
		}
		
		String birthDay = mortgageRequsetDTO.getDateOfBirth();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dob = LocalDate.parse(birthDay, formatter);
        if(!validAge(dob)) {
        	throw new CustomError("Sorry we are unable to grant you the mortgage at this moment");
        }		
		if(!validPhoneNumber(mortgageRequsetDTO.getPhoneNumber())) {
			throw new CustomError("Please provide valid number");
		}
		String joinDate = mortgageRequsetDTO.getDateOfJoining();
		LocalDate doj = LocalDate.parse(joinDate, formatter);
		Mortgage mortgage = new Mortgage();
		BeanUtils.copyProperties(mortgageRequsetDTO, mortgage,"dateOfJoining","dateOfBirth");
		mortgage.setDateOfBirth(dob);
		mortgage.setDateOfJoining(doj);
		mortgage.setCustomer(customer);
		mortgageRepository.save(mortgage);
		MortgageResponseDTO mortgageResponseDTO = new MortgageResponseDTO(); 
		mortgageResponseDTO.setLoginId(customer.getLoginId());
		mortgageResponseDTO.setPassword(customer.getPassword());
		mortgageResponseDTO.setAccountNumber(transactionalAccount.getAccountNumber());
		mortgageResponseDTO.setMortgageNumber(mortgageAccount.getAccountNumber());
		mortgageResponseDTO.setCustomerName(customer.getCustomerName());
			
		return mortgageResponseDTO;
	}
	
	private boolean validPhoneNumber(Long number) {
		String num = number.toString();
		Pattern p = Pattern.compile("^[0-9]{10}$"); 
        Matcher m = p.matcher(num); 
        return (m.find() && m.group().equals(num)); 
	}
	
	private boolean validAge(LocalDate date1) {
		boolean result=false;
		int birthYear = date1.getYear();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int age = year - birthYear;
		if(age>18) {
			result =true;
		}return result;	
	}

	@Override
	public List<AccountDTO> fetchAccountByCustomerId(Long customerId) {
		LOGGER.info("fetchAccountByCustomerId");
		AccountDTO accountDTO = null;
		List<AccountDTO> listAccountDTO = null;
		listAccountDTO = new ArrayList<>();
		List<Account> listAccount = accountRepository.findByCustomerId(customerId);
		if (!listAccount.isEmpty()) {
			for (Account account : listAccount) {
				accountDTO = new AccountDTO();
				accountDTO.setAccountId(account.getAccountId());
				accountDTO.setAccountNumber(account.getAccountNumber());
				accountDTO.setAccountType(account.getAccountType());
				accountDTO.setBalance(account.getBalance());
				accountDTO.setDate(account.getDate());
				listAccountDTO.add(accountDTO);

			}
			return listAccountDTO;
		}
		else {
			 throw new CustomerAccountNotFound(customerId);
		}

	}

}
