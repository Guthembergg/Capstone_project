package com.spring_security_project.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.spring_security_project.auth.entity.User;

import com.spring_security_project.model.Dream;
@Repository
public interface DreamRepository extends JpaRepository<Dream, Long>, PagingAndSortingRepository<Dream, Long> {
	 //List<Dream> findDreamByUser(User u);
	 @Query("SELECT c FROM Dream c WHERE c.user.username = :username")
	 List<Dream> findDreamByUsername(String username);
	 @Query("SELECT c FROM Dream c WHERE c.user.username = :username")
	 Page<Dream> findDreamsByUser(String username, Pageable pageable);


}