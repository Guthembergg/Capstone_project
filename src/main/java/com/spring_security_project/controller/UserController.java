package com.spring_security_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_security_project.auth.entity.User;
import com.spring_security_project.auth.payload.JWTAuthResponse;
import com.spring_security_project.auth.payload.SubscribeDTO;
import com.spring_security_project.service.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired UserService service;
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> recuperaUtenti(){
		return new ResponseEntity<>(service.findAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/subscribe")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> Subscribe( @RequestBody SubscribeDTO subscribeDTO) {
		try {
		User u = service.findByUsername(subscribeDTO.getUsername());
		u.setSubbed(subscribeDTO.getSubbed());
		return new ResponseEntity<User>(service.editUtente(u), HttpStatus.CREATED);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}

	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaUtenti(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	@GetMapping("username/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaUtentiUsername(@PathVariable String username){
		try {
			return new ResponseEntity<>(service.findByUsername(username), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')or hasRole('USER') ")
	public ResponseEntity<?> modificaUtente(@RequestBody User u, @PathVariable Long id){
		u.setId(id);
		try {return new ResponseEntity<User>(service.editUtente(u), HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}		 
	}
}
