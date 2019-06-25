package com.ing.mortgage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { UserNotFoundException.class })
	public ResponseEntity<ResponseError> loginError(Exception e) {
		ResponseError loginError = new ResponseError(e.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(loginError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { CustomerAccountNotFound.class })
	public ResponseEntity<ResponseError> accountError(Exception e) {
		ResponseError accountError = new ResponseError(e.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(accountError, HttpStatus.NOT_FOUND);
	}

}
