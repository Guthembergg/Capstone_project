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

import com.spring_security_project.model.Alarm;
import com.spring_security_project.model.Dream;
import com.spring_security_project.service.AlarmService;
import com.spring_security_project.service.DreamService;
import com.spring_security_project.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alarms")
public class AlarmController {
	@Autowired AlarmService service;
	@Autowired UserService serviceU;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaAllarme(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.findAlarmById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	
	@GetMapping("username/{username}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> trovaAllarmebyUsername(@PathVariable String username){
		try {
			return new ResponseEntity<>(service.findAllAlarmByUsername(username), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	
	
	@PostMapping("username/{username}")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public ResponseEntity<?> registraAllarme(@RequestBody Alarm c,@PathVariable String username){
		try {
			return new ResponseEntity<Alarm>(serviceU.associaAllarmeUtenteUsername(username, c), HttpStatus.CREATED);			
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/{username}/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> modificaAllarmeUsername(@RequestBody Alarm c, @PathVariable Long id, @PathVariable String username){
		try {
			c.setId(id);
			c.setUser(serviceU.findByUsername(username));
		return new ResponseEntity<Alarm>(service.editAlarm(c), HttpStatus.CREATED);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}		 
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> eliminaAllarme(@PathVariable Long id){
		try {
			return new ResponseEntity<>(service.deleteAlarmById(id), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FOUND);
		}
	}
	
	
}
