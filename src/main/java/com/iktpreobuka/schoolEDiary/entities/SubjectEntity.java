package com.iktpreobuka.schoolEDiary.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.schoolEDiary.security.Views;


public class SubjectEntity{
	private Integer id;
	private String name;
	private Integer weeklyNumberOfLectures;
	
	@JsonView(Views.Private.class)
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subjectYear")
	private SchoolYearEntity subjectYear;

	//	@JsonView(Views.Private.class)
//	@JsonManagedReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "Teacher_Subject", joinColumns = {
			@JoinColumn(name = "Subject_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "Teacher_id", nullable = false, updatable = false) })
	protected Set<TeacherEntity> teachers = new HashSet<TeacherEntity>();
	
	
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

	public SchoolYearEntity getSubjectYear() {
		return subjectYear;
	}

	public void setSubjectYear(SchoolYearEntity subjectYear) {
		this.subjectYear = subjectYear;
	}

	public Set<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(Set<TeacherEntity> teachers) {
		this.teachers = teachers;
	}
	
	
}
