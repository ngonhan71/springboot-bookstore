package com.nhan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private int bookId;
	
	@Column(name = "name", columnDefinition = "varchar(255)", unique = true, length = 255)
	private String name;
	
	@Column(name= "slug", unique = true, length = 255)
	private String slug;
	
	@Column(name = "author", columnDefinition = "varchar(255)")
	private String author;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "discount", columnDefinition = "integer default 0")
	private int discount;
	
	@Column(name = "image_url", columnDefinition = "varchar(255)")
	private String imageUrl;
	
	@Column(name = "description", columnDefinition = "text")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

}
