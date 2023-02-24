package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.User;

public interface UserService {
	Optional<User> saveOrUpdate(User user);
	
	Optional<User> findByUserName(String userName);
	
	List<User> findAll();

	Boolean existsByUserName(String userName);

	Boolean existsByEmail(String email);
	
	void deleteById(String id);
	
	void updateLastLoginDate(User user);
	
	Optional<User> findById(String id);
}