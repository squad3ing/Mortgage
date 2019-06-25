package com.ing.mortgage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.mortgage.dto.AccountDTO;
import com.ing.mortgage.dto.MortgageRequsetDTO;
import com.ing.mortgage.dto.MortgageResponseDTO;
import com.ing.mortgage.service.MortgageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = { "*", "/" }, origins = { "*", "/" })
public class MortgageController {
	@Autowired
	MortgageService mortgageService;

	@PostMapping("/mortgage")
	public ResponseEntity<MortgageResponseDTO> mortgage(@RequestBody MortgageRequsetDTO mortgageRequsetDTO) {
		MortgageResponseDTO mortgageResponseDTO = mortgageService.createMortgage(mortgageRequsetDTO);
		return new ResponseEntity<>(mortgageResponseDTO, HttpStatus.CREATED);

	}

	@GetMapping("/accounts/{customerId}")
	public ResponseEntity<List<AccountDTO>> getAccountSummary(@PathVariable Long customerId) {
		List<AccountDTO> listDTO = mortgageService.fetchAccountByCustomerId(customerId);
		return new ResponseEntity<>(listDTO, HttpStatus.OK);
	}
}
