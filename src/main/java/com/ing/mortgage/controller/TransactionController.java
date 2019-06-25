package com.ing.mortgage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.mortgage.dto.TransactionDTO;
import com.ing.mortgage.service.TransactionService;

@RestController
@CrossOrigin(allowedHeaders = {"*","*/"},origins = {"*","*/"})
@RequestMapping("api")
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	TransactionService transactionService;
	@PutMapping("/transactions")
	public ResponseEntity<List<TransactionDTO>> transactions(@RequestBody String accountNumber){
		List<TransactionDTO> transactionDTO = transactionService.getTransactions(accountNumber);
		LOGGER.info("transaction list");
		return new ResponseEntity<>(transactionDTO,HttpStatus.FOUND);
		}
	
}
