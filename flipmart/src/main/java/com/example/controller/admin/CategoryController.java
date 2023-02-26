package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Category;
import com.example.model.CategoryDetail;
import com.example.payload.request.CategoryRequest;
import com.example.payload.response.ListResponse;
import com.example.payload.response.PageResponse;
import com.example.payload.response.MessageResponse;
import com.example.service.CategoryDetailService;
import com.example.service.CategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryDetailService categoryDetailService;

	@GetMapping(value = "")
	public ResponseEntity<ListResponse<Category>> findAll() {
		ListResponse<Category> data = new ListResponse<>();
		data.setData(categoryService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@ModelAttribute CategoryRequest categoryRequest) {
		Category category = categoryService.saveOrUpdate(new Category(categoryRequest.getName())).get();
		categoryRequest.getArray().forEach(Item -> {
			categoryDetailService.saveOrUpdate(new CategoryDetail(Item, category));
		});
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Thêm mới danh mục thành công!"));
	}

	@GetMapping(value = "/page")
	public ResponseEntity<PageResponse<Category>> findAllPage(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageResponse<Category> data = new PageResponse<>();
		data.setData(categoryService.findAll(PageRequest.of(page, size)));
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
}