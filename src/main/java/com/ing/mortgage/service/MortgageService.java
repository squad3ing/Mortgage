package com.ing.mortgage.service;

import java.util.List;

import com.ing.mortgage.dto.AccountDTO;

public interface MortgageService {

	List<AccountDTO> findByCustomerId(Long customerId);

}
