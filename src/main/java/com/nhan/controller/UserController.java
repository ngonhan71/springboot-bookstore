package com.nhan.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhan.entity.User;
import com.nhan.model.ResponseObject;
import com.nhan.model.UserModel;
import com.nhan.service.IUserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	@Autowired
	IUserService userService;
	
	@GetMapping("")
	public ResponseEntity<ResponseObject> getAll() {
		List<User> users = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", users));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable int id) {
		Optional<User> users = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", users));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestBody UserModel model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user.getUserId() != id) {
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("ERROR", "Forbidden", null));
		}
		User entity = new User();
		BeanUtils.copyProperties(model, entity);
		entity.setUserId(id);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Ok", userService.updateName(entity)));
	}
}
