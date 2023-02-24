package com.example.service;

import java.util.Optional;

import com.example.model.ProductColor;

public interface ProductColorService {
	Optional<ProductColor> saveOrUpdate(ProductColor productColor);
}
