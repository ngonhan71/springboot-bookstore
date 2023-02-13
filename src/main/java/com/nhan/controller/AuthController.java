package com.nhan.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhan.entity.User;
import com.nhan.model.AuthResponse;
import com.nhan.model.ResponseObject;
import com.nhan.model.UserLogin;
import com.nhan.model.UserModel;
import com.nhan.model.UserRegister;
import com.nhan.service.IUserService;
import com.nhan.utils.JWT;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWT jwt;
	
	@Autowired
	IUserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegister model, HttpServletRequest request) {
		User entity = new User();
		BeanUtils.copyProperties(model, entity);
		entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
		entity.setRole("CUSTOMER");
		entity.setStatus("INACTIVE");
		User result = userService.save(entity);
		return ResponseEntity.status(HttpStatus.OK).body(
				new ResponseObject("Ok", "Register thành công", result));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLogin model) {
		try {
			Authentication authentication = authManager.authenticate(
					new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword()));
			User user = (User) authentication.getPrincipal();
			
			user.setPassword("");
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(user, userModel);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("email", user.getEmail());
			
			String accessToken = jwt.generateToken(map, user);
			AuthResponse authRes = new AuthResponse(userModel, accessToken);
			return ResponseEntity.status(HttpStatus.OK).body(
					new ResponseObject("Ok", "Login thành công", authRes));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Login thất bại", null));
		}
		
	}
}
