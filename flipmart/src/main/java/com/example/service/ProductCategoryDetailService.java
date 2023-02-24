package com.example.service;

import java.util.Optional;

import com.example.model.ProductCategoryDetail;

public interface ProductCategoryDetailService {
	Optional<ProductCategoryDetail> saveOrUpdate(ProductCategoryDetail productCategoryDetail);
}
