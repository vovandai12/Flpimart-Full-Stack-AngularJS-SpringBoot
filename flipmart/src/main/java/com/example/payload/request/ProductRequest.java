package com.example.payload.request;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	
	private String name;
	private float price;
	private float discount = 0;
	private Date startDayDiscount = null;
	private Date endDayDiscount = null;
	private int quantity;
	private String description;
	private String brandId;
	private ArrayList<String> colorId;
	private ArrayList<String> categoryId;
	private MultipartFile[] file;
}
