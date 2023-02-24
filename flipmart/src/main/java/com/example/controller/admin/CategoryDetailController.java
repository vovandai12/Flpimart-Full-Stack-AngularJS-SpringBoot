package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Category;
import com.example.model.CategoryDetail;
import com.example.payload.response.ListResponse;
import com.example.service.CategoryDetailService;
import com.example.service.CategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/category-detail")
public class CategoryDetailController {

	@Autowired
	private CategoryDetailService categoryDetailService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(value = "")
	public ResponseEntity<ListResponse<CategoryDetail>> findAll() {
		ListResponse<CategoryDetail> data = new ListResponse<>();
		data.setData(categoryDetailService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@GetMapping(value = "/{category-id}")
	public ResponseEntity<ListResponse<CategoryDetail>> findAllByCategory(@PathVariable(name = "category-id") String id) {
		Category category = categoryService.findById(id).get();
		ListResponse<CategoryDetail> data = new ListResponse<>();
		data.setData(categoryDetailService.findAllByCategory(category));
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
}