package com.iktpreobuka.schoolEDiary.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import helper.ESemester;

public class SchoolYearEntity {
	private Integer id;
	private Integer schoolYear;
	private ESemester semester;

	@OneToMany(mappedBy = "subjectYear", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<SubjectEntity> subjects = new ArrayList<>();
	
	@OneToMany(mappedBy = "student_school_year", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<StudentEntity> students = new ArrayList<>();

	public SchoolYearEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public ESemester getSemester() {
		return semester;
	}

	public void setSemester(ESemester semester) {
		this.semester = semester;
	}

	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}
	
	

}
