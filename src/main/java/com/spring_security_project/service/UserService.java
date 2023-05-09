package com.spring_security_project.service;

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

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService {
	@Autowired
	UserRepository repo;
	@Autowired
	DayRepository repoD;

	public List<User> findAllUsers() {

		return repo.findAll();
	}
	
	public Page<User> getAllUsersPageable(Pageable pageable){
		if(repo.findAll(pageable).isEmpty()) {
			throw new EntityNotFoundException("Nessun utente in archivio");
		} else return repo.findAll(pageable);
	}
	
	public User findById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun utente associato a questo ID");
		}
		return repo.findById(id).get();
	}
	public String deleteUserById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun utente trovato");
		}
		repo.deleteById(id);
		return "utente eliminato";
	}
	public Day associaGiornoUtente(Long id, Day day) {
		if (repo.existsById(id)) {
			User u = repo.findById(id).get();
			u.getDays().add(day);
			day.setUser(u);
			repo.save(u);
			
			return repoD.save(day);
		} else
			throw new EntityNotFoundException("utente con quell id non esiste");
	}
	



	public User editUtente(User u) {
		if (!repo.existsById(u.getId())) {
			throw new EntityNotFoundException("Nessun utente trovato");
		}
		return repo.save(u);
	}

}
