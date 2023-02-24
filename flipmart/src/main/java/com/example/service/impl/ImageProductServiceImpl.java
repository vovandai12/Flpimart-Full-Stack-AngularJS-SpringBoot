package com.example.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ImageProduct;
import com.example.repository.ImageProductRepository;
import com.example.service.ImageProductService;

@Service
public class ImageProductServiceImpl implements ImageProductService {

	@Autowired
	private ImageProductRepository imageProductRepository;

	@Override
	public Optional<ImageProduct> saveOrUpdate(ImageProduct imageProduct) {
		if (imageProduct == null)
			return Optional.empty();
		ImageProduct imageProductOld = imageProductRepository.save(imageProduct);
		return Optional.of(imageProductOld);
	}

}
