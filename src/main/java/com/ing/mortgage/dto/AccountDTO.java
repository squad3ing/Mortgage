package com.ing.mortgage.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	private Long accountId;
	private String accountNumber;
	private String accountType;
	private Double balance;
	private LocalDate date;

}
