package com.spring_security_project.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_security_project.auth.entity.User;
import com.spring_security_project.auth.payload.JWTAuthResponse;
import com.spring_security_project.auth.payload.LoginDto;
import com.spring_security_project.auth.payload.RegisterDto;
import com.spring_security_project.auth.repository.UserRepository;
import com.spring_security_project.auth.service.AuthService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService;
@Autowired
UserRepository repo;
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	// Build Login REST API
	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

		String token = authService.login(loginDto);
		Boolean subbed= repo.findByUsername(loginDto.getUsername()).get().getSubbed();
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setUsername(loginDto.getUsername());
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setSubbed(subbed);
		return ResponseEntity.ok(jwtAuthResponse);
	}
	

	// Build Register REST API
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User u = repo.findByEmail(auth.getName()).get();
		return new ResponseEntity<>(u, HttpStatus.OK);
	}
}
