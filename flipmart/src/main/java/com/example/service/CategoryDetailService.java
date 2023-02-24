package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.model.Category;
import com.example.model.CategoryDetail;

public interface CategoryDetailService {
	Optional<CategoryDetail> saveOrUpdate(CategoryDetail categoryDetail);

	List<CategoryDetail> findAll();

	void deleteById(String id);

	Optional<CategoryDetail> findById(String id);

	List<CategoryDetail> findAllByCategory(Category category);
}
