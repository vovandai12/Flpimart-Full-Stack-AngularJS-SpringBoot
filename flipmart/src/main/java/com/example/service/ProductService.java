package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Product;

public interface ProductService {
	Optional<Product> saveOrUpdate(Product product);
	
	List<Product> findAll();
	
	void deleteById(String id);
	
	Optional<Product> findById(String id);
}