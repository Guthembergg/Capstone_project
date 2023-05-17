package com.spring_security_project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring_security_project.auth.entity.User;
import com.spring_security_project.auth.repository.UserRepository;

import com.spring_security_project.model.Dream;

import com.spring_security_project.repository.DreamRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DreamService {
	@Autowired
	DreamRepository repo;
	@Autowired
	UserRepository repoU;

	public List<Dream> findAllDream() {

		return repo.findAll();
	}

	public List<Dream> findAllbyUsername(String username) {
		if (repo.findDreamByUsername(username).isEmpty()) {
			throw new EntityNotFoundException("Nessun sogno associato a questo username");
		}
		return repo.findDreamByUsername(username);
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

	public Page<Dream> findAllbyUsernamePageable(String username, Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending().and(Sort.by("id").descending()));
		Page<Dream> pagedResult = repo.findDreamsByUser(username, paging);

		if (pagedResult.hasContent()) {
			return pagedResult;
		} else {
			throw new EntityNotFoundException("Nessun sogno trovato");
		}
	}
}

//	public List<Dream> findDreamByUserID(Long id) {
//		if (!repoU.existsById(id)) {
//			throw new EntityNotFoundException("Nessun utente trovato");
//		}
//		return repo.findDreamByUser(repoU.findById(id).get());
//	}
