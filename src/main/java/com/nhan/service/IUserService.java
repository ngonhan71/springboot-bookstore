package com.nhan.service;

import java.util.List;
import java.util.Optional;

import com.nhan.entity.User;

public interface IUserService {

	Optional<User> findById(Integer id);
	
	Optional<User> findByEmail(String email);

	List<User> findAll();

	<S extends User> S save(S entity);

	int updateName(User entity);


}
