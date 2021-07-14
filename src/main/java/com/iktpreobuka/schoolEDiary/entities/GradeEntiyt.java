package com.iktpreobuka.schoolEDiary.entities;

import helper.EGradeType;

public class GradeEntiyt {
//	@JsonView(Views.Private.class)
//	@JsonProperty("ID")
	private Integer id;
	private String name;
	private EGradeType gradeType;
	
	public GradeEntiyt() {
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
