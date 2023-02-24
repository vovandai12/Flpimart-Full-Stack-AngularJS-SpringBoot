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

import com.example.model.Color;
import com.example.payload.request.ColorRequest;
import com.example.payload.response.ListResponse;
import com.example.payload.response.MessageResponse;
import com.example.service.ColorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/color")
public class ColorController {
	
	@Autowired
	private ColorService colorService;
	
	@GetMapping(value = "")
	public ResponseEntity<ListResponse<Color>> findAll() {
		ListResponse<Color> data = new ListResponse<>();
		data.setData(colorService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@ModelAttribute ColorRequest colorRequest) {
		Color color = new Color(colorRequest.getName());
		colorService.saveOrUpdate(color);
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Thêm mới màu sắc thành công!"));
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		colorService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}