package com.ing.mortgage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ing.mortgage.dto.AccountDTO;
import com.ing.mortgage.dto.MortgageRequsetDTO;
import com.ing.mortgage.dto.MortgageResponseDTO;
import com.ing.mortgage.entity.Account;
import com.ing.mortgage.repository.AccountRepository;

@Service
public class MortgageServiceImpl implements MortgageService {

	private AccountRepository accountRepository;

	@Override
	public MortgageResponseDTO createMortgage(MortgageRequsetDTO mortgageRequsetDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccountDTO> fetchAccountByCustomerId(Long customerId) {
		AccountDTO accountDTO = null;
		List<AccountDTO> listAccountDTO = null;
		listAccountDTO = new ArrayList<>();
		List<Account> listAccount = accountRepository.findByCustomerId(customerId);
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

}
