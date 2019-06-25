package com.ing.mortgage.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.mortgage.dto.AccountDTO;
import com.ing.mortgage.dto.MortgageRequsetDTO;
import com.ing.mortgage.dto.MortgageResponseDTO;
import com.ing.mortgage.entity.Account;
import com.ing.mortgage.entity.Customer;
import com.ing.mortgage.entity.Transaction;
import com.ing.mortgage.exception.CustomerAccountNotFound;
import com.ing.mortgage.repository.AccountRepository;
import com.ing.mortgage.repository.CustomerRepository;

@Service
public class MortgageServiceImpl implements MortgageService {
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

		List<Transaction> transactions = new ArrayList<>();
		if (mortgageRequsetDTO.getPropertyCost() >= 100000 && mortgageRequsetDTO.getDeposit() > 0) {
			Account transactionalAccount = new Account();
			transactionalAccount.setBalance(mortgageRequsetDTO.getPropertyCost() - mortgageRequsetDTO.getDeposit());
			transactionalAccount.setAccountNumber("ACC25");
			transactionalAccount.setAccountType("Transactional Account");
			transactionalAccount.setDate(LocalDate.now());
			transactionalAccount.setCustomer(customer);
			transactionalAccount.setTransactions(transactions);
			accountRepository.save(transactionalAccount);

			Account mortgageAccount = new Account();
			mortgageAccount.setBalance(-(mortgageRequsetDTO.getPropertyCost() - mortgageRequsetDTO.getDeposit()));
			mortgageAccount.setAccountNumber("MORT25");
			mortgageAccount.setAccountType("Mortgage Account");
			mortgageAccount.setDate(LocalDate.now());
			mortgageAccount.setCustomer(customer);
			mortgageAccount.setTransactions(transactions);
			accountRepository.save(mortgageAccount);
		} 
		
		else
			return null;

		return null;
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
