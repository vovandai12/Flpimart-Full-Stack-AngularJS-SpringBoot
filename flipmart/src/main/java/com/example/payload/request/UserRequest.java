package com.example.payload.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.common.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	private String fullName;
	private String email;
	private String address;
	private Date birthDay;
	private Boolean login;
	private Boolean gender;
	private Role role;
	private MultipartFile file;
}
