package com.spring_security_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring_security_project.model.Dream;
import com.spring_security_project.service.DreamService;
import com.spring_security_project.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dreams")
public class DreamController {
	@Autowired DreamService service;
	@Autowired UserService serviceU;
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> recuperaSogni(){
		return new ResponseEntity<>(service.findAllDream(), HttpStatus.OK);
	}
	

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaSogno(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
//	@GetMapping("users/{id}")
//	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//	public ResponseEntity<?> trovaSognobyuserid(@PathVariable Long id){
//		try {
//			return new ResponseEntity<>(service.findDreamByUserID(id), HttpStatus.OK);
//		} catch(Exception e) {
//			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
//		}
//	}
	@PutMapping("/{username}/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> modificaSogno(@RequestBody Dream c, @PathVariable Long id, @PathVariable String username){
		try {
			c.setId(id);
			c.setUser(serviceU.findByUsername(username));
		return new ResponseEntity<Dream>(service.editDream(c), HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}		 
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> eliminaSogno(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.deleteDreamById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public ResponseEntity<?> registraSogni(@RequestBody Dream c,@PathVariable Long id){
		try {
			return new ResponseEntity<Dream>(serviceU.associaSognoUtente(id, c), HttpStatus.CREATED);			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("username/{username}")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public ResponseEntity<?> registraGiorni(@RequestBody Dream c,@PathVariable String username){
		try {
			return new ResponseEntity<Dream>(serviceU.associaSognoUtenteUsername(username, c), HttpStatus.CREATED);			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("username/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaSognibyUsername(@PathVariable String username){
		try {
			return new ResponseEntity<>(service.findAllbyUsername(username), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	
	
	 @GetMapping("usernameP/{username}")
	    public ResponseEntity<Page<Dream>> getAllDreamsP(
	    		@PathVariable String username,
	                        @RequestParam(defaultValue = "0") Integer pageNo,
	                        @RequestParam(defaultValue = "10") Integer pageSize,
	                        @RequestParam(defaultValue = "date") String sortBy)
	    {
	        Page<Dream> list = service.findAllbyUsernamePageable(username,pageNo, pageSize, sortBy);

	        return new ResponseEntity<Page<Dream>>(list, HttpStatus.OK);
	    }
	
}
