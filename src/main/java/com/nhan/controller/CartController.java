package com.nhan.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhan.entity.CartItem;
import com.nhan.entity.User;
import com.nhan.model.CartItemModel;
import com.nhan.model.ResponseObject;
import com.nhan.service.ICartService;

@RestController
@RequestMapping("api/v1/carts")
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	@GetMapping("")
	public ResponseEntity<ResponseObject> getByUserId() {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<CartItem> cart = cartService.findByUserId(currentUser.getUserId());
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", cart));
	}
	
	@PostMapping("")
	public ResponseEntity<ResponseObject> addToCart(@Valid @RequestBody CartItemModel model) {
		
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Thêm vào giỏ hàng thành công!", cartService.addToCart(currentUser.getUserId(), model)));
	}
	
	@PutMapping("")
	public ResponseEntity<ResponseObject> updateQuantity(@Valid @RequestBody CartItemModel model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Cập nhật thành công!", cartService.updateQuantity(currentUser.getUserId(), model)));
	}
	
	@DeleteMapping("")
	public ResponseEntity<ResponseObject> removeItem(@RequestBody CartItemModel model) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Xóa sản phẩm khỏi giỏ hàng thành công!", cartService.removeItem(currentUser.getUserId(), model.getBookId())));
	}

}
