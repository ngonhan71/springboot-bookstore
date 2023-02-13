package com.nhan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@IdClass(CartId.class)
@Table(name = "cart_items")
public class CartItem {
	
	@Id
	@Column(name = "book_id")
	private Book book;
	
	@Id
	@Column(name = "user_id")
	@JsonIgnore
	private User user;
	
	@Column(name = "quantity")
	private int quantity = 1;

	@Override
    public String toString() {
        return this.book.getBookId() + " : " + this.user.getEmail() + " : " + this.quantity ;
    }
	
}
