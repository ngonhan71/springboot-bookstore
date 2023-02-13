package com.nhan.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nhan.entity.User;
import com.nhan.exception.EntityExistsException;
import com.nhan.exception.NotFoundException;
import com.nhan.repository.UserRepository;
import com.nhan.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService  {

	@Autowired
	UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public <S extends User> S save(S entity) {
		
		Optional<User> check = userRepository.findByEmail(entity.getEmail());
		
		if (check.isPresent()) {
			throw new EntityExistsException("Email đã tồn tại!");
		}
				
		return userRepository.save(entity);
	}
	
	@Override
	public int updateName(User entity) {
		if (userRepository.updateName(entity.getUserId(), entity.getName()) == 1)
			return 1;
		throw new NotFoundException("User không tồn tại!");
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent())
			return user;
		throw new NotFoundException("User không tồn tại!");
	}
	
	
	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username).get();
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
	
}
