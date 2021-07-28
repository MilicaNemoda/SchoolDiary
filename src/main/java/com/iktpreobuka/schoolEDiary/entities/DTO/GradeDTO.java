package com.iktpreobuka.schoolEDiary.entities.DTO;

import javax.validation.constraints.NotNull;

import helper.EGradeType;

public class GradeDTO {
	@NotNull(message = "Grade must be provided.")
	private Integer grade;
	@NotNull(message = "Grade type name must be provided.")
	private EGradeType gradeType;
	@NotNull(message = "Student's username must be provided.")
	private String studentUsername;
	@NotNull(message = "Subject must be provided.")
	private String subjectName;
	
	public GradeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EGradeType getGradeType() {
		return gradeType;
	}

	public void setGradeType(EGradeType gradeType) {
		this.gradeType = gradeType;
	}

	public String getStudentUsername() {
		return studentUsername;
	}

	public void setStudentUsername(String studentUsername) {
		this.studentUsername = studentUsername;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	
	
}
