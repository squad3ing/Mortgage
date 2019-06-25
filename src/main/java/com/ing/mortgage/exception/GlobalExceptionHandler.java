package com.ing.mortgage.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(value = {CustomError.class})
	public ResponseEntity<ResponseError> mapException(Exception e){
		ResponseError error = new ResponseError(e.getMessage(),org.springframework.http.HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(error,org.springframework.http.HttpStatus.BAD_REQUEST);
		
	}
}
