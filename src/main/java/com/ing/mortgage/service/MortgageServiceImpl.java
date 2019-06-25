package com.ing.mortgage.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.mortgage.dto.MortgageRequsetDTO;
import com.ing.mortgage.dto.MortgageResponseDTO;
import com.ing.mortgage.entity.Mortgage;
import com.ing.mortgage.exception.CustomError;
import com.ing.mortgage.repository.MortgageRepository;

@Service
public class MortgageServiceImpl implements MortgageService{
	
	@Autowired
	MortgageRepository  mortgageRepository; 

	@Override
	public MortgageResponseDTO createMortgage(MortgageRequsetDTO mortgageRequsetDTO) {
		
		String birthDay = mortgageRequsetDTO.getDateOfBirth();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
		
		mortgageRepository.save(mortgage);
		
		
		
		return null;
	}
	
	private boolean validPhoneNumber(Long number) {
		String num = number.toString();
		Pattern p = Pattern.compile("^[0-9]{10}$"); 
        Matcher m = p.matcher(num); 
        return (m.find() && m.group().equals(num)); 
	}
	
	private boolean validAge(LocalDate date1) {
		
		int birthYear = date1.getYear();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int age = year - birthYear;
		if(age>18) {
			return true;
		}else {
			return false;
		}
		
		
	}

}
