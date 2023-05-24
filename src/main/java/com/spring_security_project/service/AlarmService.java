package com.spring_security_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring_security_project.model.Alarm;
import com.spring_security_project.model.Dream;
import com.spring_security_project.repository.AlarmRepository;
import com.spring_security_project.repository.DreamRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AlarmService {
	@Autowired
	AlarmRepository repo;
	public List<Alarm> findAllAlarm() {

		return repo.findAll();
	}
	
	public List<Alarm> findAllAlarmByUsername(String username) {
		if (repo.findAlarmByUsername(username).isEmpty()) {
			throw new EntityNotFoundException("Nessun allarme associato a questo username");
		}
		return repo.findAlarmByUsername(username);
	}
	public Alarm findAlarmById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun allarme associato a questo ID");
		}
		return repo.findById(id).get();
	}

	public String deleteAlarmById(Long id) {
		if (!repo.existsById(id)) {
			throw new EntityNotFoundException("Nessun allarme trovato");
		}
		repo.deleteById(id);
		return "allarme eliminato";
	}

	public String addAlarm(Alarm alarm) {

		repo.save(alarm);
		return "Allarme aggiunto";
	}
	
	public Alarm editAlarm(Alarm alarm) {
		if (!repo.existsById(alarm.getId())) {
			throw new EntityNotFoundException("Nessun allarme trovato");
		}
		return repo.save(alarm);
	}

}
