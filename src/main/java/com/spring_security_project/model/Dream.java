package com.spring_security_project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.spring_security_project.auth.entity.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "dreams")
public class Dream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column
    private Double time;
    @Column()
    private List<Emotions> emotions= new ArrayList<>();
    @Column()
    private List<DreamType> type= new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Day day;
    
}
