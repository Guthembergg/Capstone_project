package com.spring_security_project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_security_project.auth.repository.UserRepository;
import com.spring_security_project.model.Day;
import com.spring_security_project.model.Dream;
import com.spring_security_project.repository.DayRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DayService {
	@Autowired
	DayRepository repo;
	@Autowired
	UserRepository repoU;
	
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
		
	public List<Day> findAllbyUsername(String username) {
		if (repo.findDayByUsername(username).isEmpty()) {
			throw new EntityNotFoundException("Nessun giorno associato a questo username");
		}
		return repo.findDayByUsername(username);
	}
	public String deleteDayById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun giorno trovato");
		}
		repo.deleteById(id);
		return "giorno eliminato";
	}

	public String addDay(Day d) {

		repo.save(d);
		return "Giorno aggiunto";
	}

	public Day editDay(Day d) {
		if (!repo.existsById(d.getId())) {
			throw new EntityNotFoundException("Nessun giorno trovato");
		}
		return repo.save(d);
	}
	public List<Day> findDayByUserID(Long id) {
		if (!repoU.existsById(id)) {
			throw new EntityNotFoundException("Nessun utente trovato");
		}
		return repo.findDayByUser(repoU.findById(id).get());
	}
//	public List<Day> findDayByDateandUsername(LocalDate date, String username) {
//		if (repoU.findByUsername(username).isEmpty()) {
//			throw new EntityNotFoundException("Nessun utente trovato");
//		}
//		return repo.findByDateAndUsername(date, username);
//	}

}
