package com.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ProductCategoryDetail;
import com.example.repository.ProductCategoryDetailRepository;
import com.example.service.ProductCategoryDetailService;

@Service
public class ProductCategoryDetailServiceImpl implements ProductCategoryDetailService {

	@Autowired
	private ProductCategoryDetailRepository productCategoryDetailRepository;

	@Override
	public Optional<ProductCategoryDetail> saveOrUpdate(ProductCategoryDetail productCategoryDetail) {
		if (productCategoryDetail == null)
			return Optional.empty();
		ProductCategoryDetail productCategoryDetailOld = productCategoryDetailRepository.save(productCategoryDetail);
		return Optional.of(productCategoryDetailOld);
	}

}
