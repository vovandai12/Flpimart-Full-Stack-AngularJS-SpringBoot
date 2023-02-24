package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Category;
import com.example.model.CategoryDetail;
import com.example.repository.CategoryDetailRepository;
import com.example.service.CategoryDetailService;

@Service
public class CategoryDetailServiceImpl implements CategoryDetailService{

	@Autowired
	private CategoryDetailRepository categoryDetailRepository;

	@Override
	public Optional<CategoryDetail> saveOrUpdate(CategoryDetail categoryDetail) {
		if (categoryDetail == null)
			return Optional.empty();
		CategoryDetail categoryDetailOld = categoryDetailRepository.save(categoryDetail);
		return Optional.of(categoryDetailOld);
	}

	@Override
	public List<CategoryDetail> findAll() {
		return categoryDetailRepository.findAll();
	}

	@Override
	public void deleteById(String id) {
		categoryDetailRepository.deleteById(id);
	}

	@Override
	public Optional<CategoryDetail> findById(String id) {
		return categoryDetailRepository.findById(id);
	}

	@Override
	public List<CategoryDetail> findAllByCategory(Category category) {
		return categoryDetailRepository.findAllByCategory(category);
	}
	
}
