package com.yolotengo.gatewayApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@Column(name="NOMBRE", nullable=false)
	private String name;

	@Column(name="MUTANTE", nullable=false)
	private Integer isMutant;
	
	@Column(name="DNA", nullable=false)
	private String dna;
	
	public User() {

	}
	
	public User(String name, int isMutant, String dna) {
		this.name = name;
		this.isMutant = isMutant;
		this.dna = dna;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsMutant() {
		return isMutant;
	}

	public void setIsMutant(Integer isMutant) {
		this.isMutant = isMutant;
	}
	
	

}
