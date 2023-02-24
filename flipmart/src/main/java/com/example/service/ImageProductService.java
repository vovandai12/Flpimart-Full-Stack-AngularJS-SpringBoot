package com.example.service;

import java.util.Optional;

import com.example.model.ImageProduct;

public interface ImageProductService {
	Optional<ImageProduct> saveOrUpdate(ImageProduct imageProduct);
}
