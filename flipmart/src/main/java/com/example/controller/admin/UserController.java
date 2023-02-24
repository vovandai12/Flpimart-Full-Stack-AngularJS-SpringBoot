package com.example.controller.admin;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.StorageFileNotFoundException;
import com.example.model.User;
import com.example.payload.request.UserEditRequest;
import com.example.payload.request.UserRequest;
import com.example.payload.response.ListResponse;
import com.example.payload.response.MessageResponse;
import com.example.service.StorageService;
import com.example.service.UserService;
import com.example.util.NumberUtil;
import com.example.util.StringUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@GetMapping(value = "")
	public ResponseEntity<ListResponse<User>> findAll() {
		ListResponse<User> data = new ListResponse<>();
		data.setData(userService.findAll());
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		if (userService.findById(id).get().getAvatar() != null) {
			try {
				storageService.delete(userService.findById(id).get().getAvatar());
			} catch (IOException e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(""));
			}
		}
		userService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@ModelAttribute UserRequest userRequest) {
		if (userService.existsByEmail(userRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Email đã được sử dụng!"));
		}
		User user = new User(userRequest.getFullName(), userRequest.getEmail(), userRequest.getAddress(),
				userRequest.getBirthDay(), userRequest.getLogin(), userRequest.getGender(), userRequest.getRole());
		if (userRequest.getFile() != null && !userRequest.getFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			user.setAvatar(storageService.getStorageFilename(userRequest.getFile(), uuidString));
			storageService.store(userRequest.getFile(), user.getAvatar());
		}
		user.setId(null);
		user.setUserName(
				StringUtil.removeAccent(userRequest.getFullName()) + String.valueOf(NumberUtil.random5Number()));
		user.setPassword(user.getUserName() + "FlipShop@");
		User userOld = userService.saveOrUpdate(user).get();
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Chúc mừng đã tạo mới người dùng "
				+ userOld.getFullName() + " thành công. Tên đăng nhập là: " + userOld.getUserName()));
	}

	@PostMapping(value = "/edit")
	public ResponseEntity<?> edit(@ModelAttribute UserEditRequest userEditRequest) {
		User user = userService.findById(userEditRequest.getId()).get();
		user.setFullName(userEditRequest.getFullName());
		user.setLogin(userEditRequest.getLogin());
		user.setGender(userEditRequest.getGender());
		if (userEditRequest.getFile() != null && !userEditRequest.getFile().isEmpty()) {
			if (user.getAvatar() != null) {
				try {
					storageService.delete(user.getAvatar());
				} catch (IOException e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(""));
				}
			}
			UUID uuid = UUID.randomUUID();
			String uuidString = uuid.toString();
			user.setAvatar(storageService.getStorageFilename(userEditRequest.getFile(), uuidString));
			storageService.store(userEditRequest.getFile(), user.getAvatar());
		}
		userService.saveOrUpdate(user);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new MessageResponse("Chúc mừng đã cập nhật người dùng thành công!"));
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