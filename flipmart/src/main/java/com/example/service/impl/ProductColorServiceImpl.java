package com.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ProductColor;
import com.example.repository.ProductColorRepository;
import com.example.service.ProductColorService;

@Service
public class ProductColorServiceImpl implements ProductColorService {

	@Autowired
	private ProductColorRepository productColorRepository;

	@Override
	public Optional<ProductColor> saveOrUpdate(ProductColor productColor) {
		if (productColor == null)
			return Optional.empty();
		ProductColor productColorOld = productColorRepository.save(productColor);
		return Optional.of(productColorOld);
	}

}
