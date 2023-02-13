package com.nhan.service;

import java.util.List;
import java.util.Optional;

import com.nhan.entity.CartId;
import com.nhan.entity.CartItem;
import com.nhan.model.CartItemModel;

public interface ICartService {

	Optional<CartItem> findById(CartId id);

	List<CartItem> findByUserId(int userId);

	<S extends CartItem> S save(S entity);

	int addToCart(int userId, CartItemModel model);

	int updateQuantity(int userId, CartItemModel model);

	void deleteById(CartId id);

	int removeItem(int userId, int bookId);

}
