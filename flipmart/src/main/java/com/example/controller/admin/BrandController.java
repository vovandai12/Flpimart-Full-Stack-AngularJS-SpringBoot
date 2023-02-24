package com.example.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Brand;
import com.example.payload.request.BrandRequest;
import com.example.payload.response.ListResponse;
import com.example.payload.response.MessageResponse;
import com.example.service.BrandService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/brand")
public class BrandController {

	@Autowired
	private BrandService brandService;

	@GetMapping(value = "")
	public ResponseEntity<ListResponse<Brand>> findAll() {
		ListResponse<Brand> data = new ListResponse<>();
		data.setData(brandService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@ModelAttribute BrandRequest brandRequest) {
		Brand brand = new Brand(brandRequest.getName());
		brandService.saveOrUpdate(brand);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Thêm mới thương hiệu thành công!"));
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		brandService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}