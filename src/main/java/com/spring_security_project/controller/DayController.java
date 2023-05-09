package com.spring_security_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_security_project.model.Day;
import com.spring_security_project.model.Dream;
import com.spring_security_project.service.DayService;
import com.spring_security_project.service.DreamService;
import com.spring_security_project.service.UserService;

@RestController
@RequestMapping("/days")
public class DayController {
	@Autowired DayService service;
	@Autowired UserService serviceU;
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> recuperaGiorni(){
		return new ResponseEntity<>(service.findAllDays(), HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public ResponseEntity<?> registraGiorni(@RequestBody Day c,@PathVariable Long id){
		try {
			return new ResponseEntity<Day>(serviceU.associaGiornoUtente(id, c), HttpStatus.CREATED);			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaGiorni(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')or hasRole('USER') ")
	public ResponseEntity<?> modificaGiorni(@RequestBody Day c, @PathVariable Long id){
		c.setId(id);
		try {return new ResponseEntity<Day>(service.editDay(c), HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}		 
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> eliminaGiorni(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.deleteDayById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
}
