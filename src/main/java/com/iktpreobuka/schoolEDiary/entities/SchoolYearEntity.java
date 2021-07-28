package com.iktpreobuka.schoolEDiary.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import helper.ESemester;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SchoolYearEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message = "Year must be provided.")
	@Min(value=1, message="School year must be 1 or higher!")
	@Max(value=8, message="School year must be 8 or less!")
	@Column(nullable = false)
	private Integer year;
//	@Column(nullable = false)
//	private ESemester semester;

	@JsonIgnore
	@OneToMany(mappedBy = "schoolYearSubject", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<SubjectEntity> subjects = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "schoolYearStudent", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

//	public ESemester getSemester() {
//		return semester;
//	}
//
//	public void setSemester(ESemester semester) {
//		this.semester = semester;
//	}

	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

}
