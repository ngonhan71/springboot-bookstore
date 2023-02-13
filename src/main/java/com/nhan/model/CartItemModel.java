package com.nhan.model;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemModel {

	@Min(value = 1, message = "Invalid")
	private int bookId;
	
	@Min(1)
	private int quantity; 
}
