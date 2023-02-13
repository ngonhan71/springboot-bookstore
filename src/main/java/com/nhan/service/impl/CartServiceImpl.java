package com.nhan.service.impl;


import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nhan.entity.Book;
import com.nhan.entity.CartId;
import com.nhan.entity.CartItem;
import com.nhan.entity.User;
import com.nhan.exception.EntityExistsException;
import com.nhan.model.CartItemModel;
import com.nhan.repository.CartRepository;
import com.nhan.service.ICartService;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	CartRepository cartRepository;

	public CartServiceImpl(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	@Override
	public int addToCart(int userId, CartItemModel model) {
		User user = new User();
		user.setUserId(userId);
		Book book = new Book();
		book.setBookId(model.getBookId());
		CartId cartId = new CartId(user, book);
		Optional<CartItem> cartItemOptional = cartRepository.findById(cartId);
		if (cartItemOptional.isPresent()) {
			throw new EntityExistsException("Sẩn phẩm này đã có trong giỏ hàng!");
		} else return cartRepository.addToCart(userId, model.getBookId(), model.getQuantity());
	}
	
	@Override
	public int removeItem(int userId, int bookId) {
		return cartRepository.removeItem(userId, bookId);
	}
	
	@Override
	public int updateQuantity(int userId, CartItemModel model) {
		return cartRepository.updateQuantity(userId, model.getBookId(), model.getQuantity());
	}
	

	@Override
	public Optional<CartItem> findById(CartId id) {
		return cartRepository.findById(id);
	}
	
	@Override
	public void deleteById(CartId id) {
		cartRepository.deleteById(id);
	}

	@Override
	public List<CartItem> findByUserId(int userId) {
		return cartRepository.findByUserId(userId);
	}

	@Override
	public <S extends CartItem> S save(S entity) {
		return cartRepository.save(entity);
	}
}
