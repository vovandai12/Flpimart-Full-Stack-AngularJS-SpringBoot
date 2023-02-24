package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Brand;
import com.example.repository.BrandRepository;
import com.example.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public Optional<Brand> saveOrUpdate(Brand brand) {
		if (brand == null)
			return Optional.empty();
		Brand brandOld = brandRepository.save(brand);
		return Optional.of(brandOld);
	}

	@Override
	public List<Brand> findAll() {
		return brandRepository.findAll();
	}

	@Override
	public void deleteById(String id) {
		brandRepository.deleteById(id);
	}

	@Override
	public Optional<Brand> findById(String id) {
		return brandRepository.findById(id);
	}

}
