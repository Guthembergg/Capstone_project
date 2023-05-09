package com.spring_security_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.spring_security_project.model.Dream;

public interface DreamRepository extends JpaRepository<Dream, Long>, PagingAndSortingRepository<Dream, Long> {

}
