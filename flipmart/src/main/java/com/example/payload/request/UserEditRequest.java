package com.example.payload.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {
	private String id;
	private String fullName;
	private Boolean login;
	private Boolean gender;
	private MultipartFile file;
}
