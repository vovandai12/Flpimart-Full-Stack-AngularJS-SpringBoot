package com.example.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Role;
import com.example.component.JwtTokenProvider;
import com.example.model.User;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.RegisterRequest;
import com.example.payload.response.MessageResponse;
import com.example.payload.response.LoginResponse;
import com.example.service.CustomUserDetailsService;
import com.example.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		CustomUserDetailsService userDetails = (CustomUserDetailsService) authentication.getPrincipal();
		String jwt = tokenProvider.generateJwtToken(authentication);
//		List<String> role = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
		userService.updateLastLoginDate(userDetails.getUser());
		return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(jwt, userDetails.getUser(), "Chúc mừng " + userDetails.getUser().getUserName() + " đăng nhập thành công!"));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
		if (userService.existsByUserName(registerRequest.getUserName())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new MessageResponse("Tên đăng nhập đã được sử dụng!"));
		}
		if (userService.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Email đã được sử dụng!"));
		}
		User user = new User(registerRequest.getUserName(), registerRequest.getFullName(), registerRequest.getEmail(),
				registerRequest.getPassword(), true, Role.ROLE_ADMIN);
		Optional<User> userOld = userService.saveOrUpdate(user);
		if (userOld.isEmpty())
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(""));
		return ResponseEntity.status(HttpStatus.OK)
				.body(new MessageResponse("Chúc mừng " + userOld.get().getFullName() + " đã đăng ký thành công!"));
	}
	// FlipShop123@
}