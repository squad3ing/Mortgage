package com.ing.mortgage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.mortgage.dto.AccountDTO;
import com.ing.mortgage.service.MortgageService;

@RestController
@CrossOrigin(allowedHeaders = { "*", "/" }, origins = { "*", "/" })
@RequestMapping("/api")
public class MortgageController {
	@Autowired
	private MortgageService mortgageService;

	@GetMapping("/api/accounts/{customerId}")
	public ResponseEntity<List<AccountDTO>> getAccountSummary(@PathVariable Long customerId) {
		List<AccountDTO> listDTO = mortgageService.findByCustomerId(customerId);
		return new ResponseEntity<>(listDTO, HttpStatus.OK);

	}

}
