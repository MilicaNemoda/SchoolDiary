package com.iktpreobuka.schoolEDiary.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.schoolEDiary.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Integer weeklyNumberOfLectures;

	@JsonView(Views.Private.class)
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolYearSubject")
	private SchoolYearEntity schoolYearSubject;

	// @JsonView(Views.Private.class)
//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Teacher_Subject", joinColumns = {
			@JoinColumn(name = "Subject_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) })
	protected Set<TeacherEntity> teachers = new HashSet<TeacherEntity>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "subjectGrade", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<GradeRecordEntity> grades = new ArrayList<>();

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

	public Integer getWeeklyNumberOfLectures() {
		return weeklyNumberOfLectures;
	}

	public void setWeeklyNumberOfLectures(Integer weeklyNumberOfLectures) {
		this.weeklyNumberOfLectures = weeklyNumberOfLectures;
	}

	public SchoolYearEntity getSchoolYear() {
		return schoolYearSubject;
	}

	public void setSchoolYear(SchoolYearEntity schoolYear) {
		this.schoolYearSubject = schoolYear;
	}

	public List<GradeRecordEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeRecordEntity> grades) {
		this.grades = grades;
	}

	public Set<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<TeacherEntity> teachers) {
		this.teachers = teachers;
	}

}
