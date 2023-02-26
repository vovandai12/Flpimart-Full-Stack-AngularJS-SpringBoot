package com.example.controller.admin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.StorageFileNotFoundException;
import com.example.model.Brand;
import com.example.model.CategoryDetail;
import com.example.model.Color;
import com.example.model.ImageProduct;
import com.example.model.Product;
import com.example.model.ProductCategoryDetail;
import com.example.model.ProductColor;
import com.example.payload.request.ProductRequest;
import com.example.payload.response.ListResponse;
import com.example.payload.response.MessageResponse;
import com.example.service.BrandService;
import com.example.service.CategoryDetailService;
import com.example.service.ColorService;
import com.example.service.ImageProductService;
import com.example.service.ProductCategoryDetailService;
import com.example.service.ProductColorService;
import com.example.service.ProductService;
import com.example.service.StorageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/product")
public class ProductController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ColorService colorService;

	@Autowired
	private CategoryDetailService categoryDetailService;

	@Autowired
	private ProductColorService productColorService;

	@Autowired
	private ProductCategoryDetailService productCategoryDetailService;

	@Autowired
	private ImageProductService imageProductService;
	
	@GetMapping(value = "")
	public ResponseEntity<ListResponse<Product>> findAll() {
		ListResponse<Product> data = new ListResponse<>();
		data.setData(productService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@ModelAttribute ProductRequest productRequest) {
		Brand brand = brandService.findById(productRequest.getBrandId()).get();
		Product product = new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getDiscount(),
				productRequest.getStartDayDiscount(), productRequest.getEndDayDiscount(), productRequest.getQuantity(),
				productRequest.getDescription(), brand);
		Product productOld = productService.saveOrUpdate(product).get();
		productRequest.getColorId().forEach(Item -> {
			Color color = colorService.findById(Item).get();
			productColorService.saveOrUpdate(new ProductColor(productOld, color));
		});
		productRequest.getCategoryId().forEach(Item -> {
			CategoryDetail categoryDetail = categoryDetailService.findById(Item).get();
			productCategoryDetailService.saveOrUpdate(new ProductCategoryDetail(productOld, categoryDetail));
		 });
		if (productRequest.getFile().length > 0) {
			for (MultipartFile multipartFile : productRequest.getFile()) {
				UUID uuid = UUID.randomUUID();
				String uuidString = uuid.toString();
				ImageProduct imageProduct = new ImageProduct(
						storageService.getStorageFilename(multipartFile, uuidString), productOld);
				storageService.store(multipartFile, imageProduct.getName());
				imageProductService.saveOrUpdate(imageProduct);
			}
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new MessageResponse("Chúc mừng đã tạo mới sản phẩm thành công!"));
	}

	@GetMapping(value = "/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.status(HttpStatus.OK)
				.header("Content-Disposition", "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}