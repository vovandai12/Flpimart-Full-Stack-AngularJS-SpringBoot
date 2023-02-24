package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Color;
import com.example.repository.ColorRepository;
import com.example.service.ColorService;

@Service
public class ColorServiceImpl implements ColorService {

	@Autowired
	private ColorRepository colorRepository;

	@Override
	public Optional<Color> saveOrUpdate(Color color) {
		if (color == null)
			return Optional.empty();
		Color colorOld = colorRepository.save(color);
		return Optional.of(colorOld);
	}

	@Override
	public List<Color> findAll() {
		return colorRepository.findAll();
	}

	@Override
	public void deleteById(String id) {
		colorRepository.deleteById(id);
	}

	@Override
	public Optional<Color> findById(String id) {
		return colorRepository.findById(id);
	}

}
