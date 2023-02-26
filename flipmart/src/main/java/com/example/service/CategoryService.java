package com.example.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import com.example.model.Category;

public interface CategoryService {
	Optional<Category> saveOrUpdate(Category category);

	List<Category> findAll();

	void deleteById(String id);

	Optional<Category> findById(String id);

	Page<Category> findAll(Pageable pageable);
}
