package com.iktpreobuka.schoolEDiary.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import helper.EGradeType;

@Entity
public class GradeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@JsonView(Views.Private.class)
//	@JsonProperty("ID")
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private EGradeType gradeType;

	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EGradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(EGradeType gradeType) {
		this.gradeType = gradeType;
	}

}
