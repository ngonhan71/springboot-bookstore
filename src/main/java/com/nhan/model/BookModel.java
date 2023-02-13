package com.nhan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {
	private int bookId;
	private String name;
	private String author;
	private Long price;
	private String imageUrl;
	private String description;
	private int categoryId;
}
