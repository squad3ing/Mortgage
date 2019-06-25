package com.ing.mortgage.service;

import com.ing.mortgage.dto.MortgageRequsetDTO;
import com.ing.mortgage.dto.MortgageResponseDTO;

public interface MortgageService {

	public MortgageResponseDTO createMortgage(MortgageRequsetDTO mortgageRequsetDTO);
	
}
