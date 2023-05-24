package com.spring_security_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring_security_project.model.Alarm;
import com.spring_security_project.model.Dream;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long>{
	 @Query("SELECT c FROM Alarm c WHERE c.user.username = :username")
	 List<Alarm> findAlarmByUsername(String username);
}
