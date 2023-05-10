package com.spring_security_project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spring_security_project.auth.entity.User;
import com.spring_security_project.auth.repository.UserRepository;
import com.spring_security_project.model.Day;
import com.spring_security_project.model.Dream;
import com.spring_security_project.repository.DayRepository;
import com.spring_security_project.repository.DreamRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DreamService {
	@Autowired
	DreamRepository repo;
	@Autowired
	UserRepository repoU;
	@Autowired
	DayRepository repoD;

	public List<Dream> findAllDream() {

		return repo.findAll();
	}

	public Page<Dream> getAllDreamPageable(Pageable pageable) {
		if (repo.findAll(pageable).isEmpty()) {
			throw new EntityNotFoundException("Nessun sogno in archivio");
		} else
			return repo.findAll(pageable);
	}

	public Dream findById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun sogno associato a questo ID");
		}
		return repo.findById(id).get();
	}

	public String deleteDreamById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun sogno trovato");
		}
		repo.deleteById(id);
		return "sogno eliminato";
	}

	public String addDream(Dream dream) {

		repo.save(dream);
		return "Sogno aggiunto";
	}

	public Dream editDream(Dream dream) {
		if (!repo.existsById(dream.getId())) {
			throw new EntityNotFoundException("Nessun sogno trovato");
		}
		return repo.save(dream);
	}

	public Dream associaSognoGiorno(Long id, Dream dream) {
		if (repoD.existsById(id)) {
			Day d = repoD.findById(id).get();
			d.getDreams().add(dream);
			dream.setDay(d);
			
			repoD.save(d);
			return repo.save(dream);
		} else throw new EntityNotFoundException("giorno con quel id non trovato");

			
	}
	
//	public List<Dream> findDreamByUserID(Long id) {
//		if (!repoU.existsById(id)) {
//			throw new EntityNotFoundException("Nessun utente trovato");
//		}
//		return repo.findDreamByUser(repoU.findById(id).get());
//	}
}
