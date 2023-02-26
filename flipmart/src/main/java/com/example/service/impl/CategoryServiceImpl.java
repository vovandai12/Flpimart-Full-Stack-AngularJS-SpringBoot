package com.example.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Optional<Category> saveOrUpdate(Category category) {
		if (category == null)
			return Optional.empty();
		Category categoryOld = categoryRepository.save(category);
		return Optional.of(categoryOld);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public void deleteById(String id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Optional<Category> findById(String id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

}
