package com.spring_security_project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.spring_security_project.auth.entity.User;
import com.spring_security_project.model.Day;
import com.spring_security_project.model.Dream;

public interface DayRepository extends JpaRepository<Day, Long>, PagingAndSortingRepository<Day, Long> {
	 List<Day> findDayByUser(User u);
	 @Query("SELECT c FROM Day c WHERE c.user.username = :username")
	 List<Day> findDayByUsername(String username);
	List<Day> findByDateAndUsername(LocalDate date, String username);
}
