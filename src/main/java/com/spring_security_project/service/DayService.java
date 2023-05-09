package com.spring_security_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.spring_security_project.model.Day;
import com.spring_security_project.repository.DayRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DayService {
	@Autowired
	DayRepository repo;
	
	public List<Day> findAllDays() {

		return repo.findAll();
	}
	
	public Page<Day> getAllDaysPageable(Pageable pageable){
		if(repo.findAll(pageable).isEmpty()) {
			throw new EntityNotFoundException("Nessun giorno in archivio");
		} else return repo.findAll(pageable);
	}
	
	public Day findById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun giorno associato a questo ID");
		}
		return repo.findById(id).get();
	}
	public String deleteDayById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun giorno trovato");
		}
		repo.deleteById(id);
		return "giorno eliminato";
	}

}
