package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Color;

public interface ColorService {
	Optional<Color> saveOrUpdate(Color color);
	
	List<Color> findAll();
	
	void deleteById(String id);
	
	Optional<Color> findById(String id);
}
