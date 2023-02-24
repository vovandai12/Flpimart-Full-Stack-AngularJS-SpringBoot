package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Brand;

public interface BrandService {
	Optional<Brand> saveOrUpdate(Brand brand);
	
	List<Brand> findAll();
	
	void deleteById(String id);
	
	Optional<Brand> findById(String id);
}
