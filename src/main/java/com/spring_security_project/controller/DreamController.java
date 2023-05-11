package com.spring_security_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import com.spring_security_project.model.Dream;
import com.spring_security_project.service.DreamService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dreams")
public class DreamController {
	@Autowired DreamService service;
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> recuperaSogni(){
		return new ResponseEntity<>(service.findAllDream(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public ResponseEntity<?> registraSogno(@RequestBody Dream c,@PathVariable Long id){
		try {
			return new ResponseEntity<Dream>(service.associaSognoGiorno(id, c), HttpStatus.CREATED);			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
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
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')or hasRole('USER') ")
	public ResponseEntity<?> modificaSogno(@RequestBody Dream c, @PathVariable Long id){
		c.setId(id);
		try {return new ResponseEntity<Dream>(service.editDream(c), HttpStatus.CREATED);
			
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
}
