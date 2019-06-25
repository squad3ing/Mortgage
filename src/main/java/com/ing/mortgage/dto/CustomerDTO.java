package com.ing.mortgage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
	private Long customerId;

	private String customerName;

	private String message;

}
